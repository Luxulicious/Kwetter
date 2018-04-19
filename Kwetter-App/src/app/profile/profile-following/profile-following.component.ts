import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {User} from '../../models/user';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {UserService} from '../../services/user/user.service';
import {Observable} from 'rxjs/Observable';
import {ProfileComponent} from '../profile.component';
import {Router} from '@angular/router';

@Component({
    selector: 'app-profile-following',
    templateUrl: './profile-following.component.html',
    styleUrls: ['./profile-following.component.css']
})
export class ProfileFollowingComponent implements OnInit {

    @Input() user: User = null;
    followings: User[] = null;

    constructor(private router: Router, private userService: UserService,
        private authService: AuthenticationService) {}

    ngOnInit() {
        //TODO Refactor this to parent component
        if (this.user == null) {
            let userId = this.authService.getSignedInUserId();
            if (userId) {
                this.fetchUser(userId);
                this.fetchFollowing(userId);
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
            this.followings = null;
            this.fetchFollowing(userId);
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

    fetchFollowing(userId: number): void {
        this.userService.getFollowing(userId)
            .subscribe(response => {
                console.log("Following:  " + response);
                let following: User[] = response["Records"];
                this.followings = following;
            },
                error => {
                    //TODO Handle this error properly
                    console.log(error);
                });
    }


    @Output() goToProfileEvent = new EventEmitter<number>();
    goToProfile(userId: number): void {
        this.goToProfileEvent.emit(userId);
        this.router.navigate(["profile/" + userId]);
    }
}
