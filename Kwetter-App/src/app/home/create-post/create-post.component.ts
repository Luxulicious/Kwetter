import {Component, OnInit, Input} from '@angular/core';
import {User} from '../../models/user';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {PostService} from '../../services/post/post.service';
import {UserService} from '../../services/user/user.service';

@Component({
    selector: 'app-create-post',
    templateUrl: './create-post.component.html',
    styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

    @Input() user: User = null;
    postContent: string;

    constructor(private userService: UserService,
        private authService: AuthenticationService,
        private postService: PostService) {}

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
        this.postService.createNewPost(userId, this.postContent)
            .subscribe(response => {
                console.log(response);
                this.postContent = "";
            },
                error => {
                    //TODO Handle this error properly
                    console.log(error);
                });
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

}
