import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {User} from '../../models/user';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {UserService} from '../../services/user/user.service';

@Component({
    selector: 'app-profile-followers',
    templateUrl: './profile-followers.component.html',
    styleUrls: ['./profile-followers.component.css']
})
export class ProfileFollowersComponent implements OnInit {

    @Input() user: User = null;
    followers: User[] = null;

    constructor(private userService: UserService,
        private authService: AuthenticationService) {}

    ngOnInit() {
        //TODO Refactor this to parent component
        if (this.user == null) {
            let userId = this.authService.getSignedInUserId();
            if (userId) {
                this.fetchUser(userId);
                this.fetchFollowers(userId);
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
            this.followers = null;
            this.fetchFollowers(userId);
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

    fetchFollowers(userId: number): void {
        this.userService.getFollowers(userId)
            .subscribe(response => {
                console.log(response);
                let followers: User[] = response["Records"];
                this.followers = followers;
            },
                error => {
                    //TODO Handle this error properly
                    console.log(error);
                });
    }

    @Output() goToProfileEvent = new EventEmitter<number>();

    goToProfile(userId: number): void {
        this.goToProfileEvent.emit(userId);
    }
}
