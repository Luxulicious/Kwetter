import {Component, OnInit, Input, EventEmitter, Output} from '@angular/core';
import {User} from '../../models/user';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {PostService} from '../../services/post/post.service';
import {UserService} from '../../services/user/user.service';
import {Post} from '../../models/post';
import {SocketService} from '../../services/socket/socket.service';

@Component({
    selector: 'app-create-post',
    templateUrl: './create-post.component.html',
    styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

    @Input() user: User = null;
    postContent: string;
    useSocket: boolean = true;

    private webSocket: WebSocket;

    constructor(private userService: UserService,
        private authService: AuthenticationService,
        private postService: PostService, private socketService: SocketService) {}

    ngOnInit() {
        //TODO Refactor this to parent component
        if (this.user == null) {
            let userId = this.authService.getSignedInUserId();
            if (userId) {
                this.fetchUser(userId);
            } else {
                //TODO Go to log-in page
            }
        }
        else {
            this.initSocket(this.user.username);
        }
    }

    initSocket(username: string) {
        if (this.webSocket == null) {
            this.webSocket = this.socketService.instantiateSocket(username);
            this.webSocket.onopen = function (message) {
                console.log("Socket connected");
            };
            this.webSocket.onmessage = function (message) {
                console.log(message.data)
            };
            this.webSocket.onclose = function (message) {
                console.log("Socket disconnected");
            };
            this.webSocket.onerror = function (message) {
                console.log("Socket error: " + message)
            };
        }
    }


    writePost(): void {
        let userId = this.authService.getSignedInUserId();
        if (!this.useSocket) {
            this.postService.createNewPost(userId, this.postContent)
                .subscribe(response => {
                    console.log(response);
                    this.postContent = "";
                    this.refreshTimeline(userId);

                },
                    error => {
                        //TODO Handle this error properly
                        console.log(error);
                    });
        } else {
            //Socket post
            this.sendSocketPost(this.user, this.postContent);
        }
    }

    private sendSocketPost(user: User, postContent: string): any {
        console.log("sendSocketPost");
        if (this.user != null) {
            if (!this.webSocket)
                this.initSocket(user.username);

            let post: Post = new Post();
            post.content = postContent;
            post.date = new Date();
            post.poster = this.user;

            this.webSocket.send(JSON.stringify(post));

        }
    }

    //TODO Refactor this to parent component
    fetchUser(userId: number): void {
        this.userService.getUser(userId)
            .subscribe(response => {
                console.log(response);
                let user: User = response["Record"];
                this.user = user;

                this.initSocket(this.user.username);
            },
                error => {
                    //TODO Handle this error properly
                    console.log(error);
                });
    }

    @Output() refreshTimelineEvent = new EventEmitter();
    refreshTimeline(userId: number): void {
        console.log("refreshTimelineEvent launched from create-post.component.ts");
        this.refreshTimelineEvent.emit(userId);
    }

}
