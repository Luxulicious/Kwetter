import {Component, OnInit, Input} from '@angular/core';
import {User} from '../../models/user';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {UserService} from '../../services/user/user.service';
import {PostService} from '../../services/post/post.service';
import {Post} from '../../models/post';

@Component({
    selector: 'app-profile-posts',
    templateUrl: './profile-posts.component.html',
    styleUrls: ['./profile-posts.component.css']
})
export class ProfilePostsComponent implements OnInit {

    @Input() user: User = null;
    posts: Post[] = null;
    postLimit: number = 100;

    constructor(private userService: UserService,
        private authService: AuthenticationService,
        private postService: PostService) {
    }

    ngOnInit() {
        //TODO Refactor this to parent component
        if (this.user == null) {
            let userId = this.authService.getSignedInUserId();
            if (userId) {
                this.fetchUser(userId);
                this.fetchRecentPostsByUser(userId, this.postLimit);
            } else {
                //TODO Go to log-in page
            }
        }
    }

    //TODO Refactor this to parent component
    //TODO Make the user displayed changable via URL parameter
    changeUser(userId: number) {
        if (userId) {
            this.fetchUser(userId);
            this.posts = null;
            this.fetchRecentPostsByUser(userId, this.postLimit)
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

    fetchRecentPostsByUser(userId: number, limit: number): void {
        this.postService.getRecentPostsByUser(userId, limit)
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
}

