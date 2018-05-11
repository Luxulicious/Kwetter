import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {User} from '../../models/user';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {UserService} from '../../services/user/user.service';
import {PostService} from '../../services/post/post.service';
import {Post} from '../../models/post';

@Component({
    selector: 'app-timeline',
    templateUrl: './timeline.component.html',
    styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {

    @Input() user: User = null;
    posts: Post[] = null;
    postLimit: number = 100;
    title: string = "Timeline";
    userId: number;

    constructor(private userService: UserService,
        private authService: AuthenticationService,
        private postService: PostService) {
    }

    ngOnInit() {
        if (this.user == null) {
            let userId = this.authService.getSignedInUserId();
            if (userId) {
                this.userId = userId;
                this.fetchUser(userId);
                this.fetchTimeline(userId, this.postLimit);
                this.title = "Timeline";
            } else {
                //TODO Go to log-in page
            }
        }
    }

    refreshTimeline() {
        //TODO Refactor this to parent component
        if (this.userId) {
            this.fetchUser(this.userId);
            this.fetchTimeline(this.userId, this.postLimit);
            this.title = "Timeline";
        } else {
            //TODO Go to log-in page
        }

    }

    //TODO Refactor this to parent component
    //TODO Make the user displayed changable via URL parameter
    changeUser(userId: number) {
        if (userId) {
            this.fetchUser(userId);
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

    fetchTimeline(userId: number, limit: number): void {
        this.postService.getTimeline(userId, limit)
            .subscribe(response => {
                console.log(response);
                let posts: Post[] = response["Records"];
                this.posts = posts;
            },
                error => {
                    //TODO Handle this error properly
                    console.log(error);
                });
    }

    setTimelineToMatchSearch(posts: Post[]) {
        this.posts = posts;
        this.title = "Results"
    }

    @Output() goToProfileEvent = new EventEmitter<number>();
    goToProfile(userId: number): void {
        this.goToProfileEvent.emit(userId);
    }
}
