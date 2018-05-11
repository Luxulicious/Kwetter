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

    //private webSocket: WebSocket = null;

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
            let socket: WebSocket = this.socketService.createSocket(user.username);

            let post: Post = new Post();
            post.content = postContent;
            post.date = new Date();
            post.poster = this.user;

            //this.webSocket.onmessage = function(e){console.log(e.data);};
            socket.onopen = () => socket.send(JSON.stringify(post));
        }
        else {
            this.sendSocketPost(user, postContent);
        }
    }

    //TODO Refactor this to parent component
    fetchUser(userId: number): void {
        this.userService.getUser(userId)
            .subscribe(response => {
                console.log(response);
                let user: User = response["Record"];
                this.user = user;
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
