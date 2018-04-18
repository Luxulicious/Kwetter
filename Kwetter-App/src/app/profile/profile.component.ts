import {Component, OnInit, ViewChild} from '@angular/core';
import {ProfileNameComponent} from './profile-name/profile-name.component';
import {ProfilePostsComponent} from './profile-posts/profile-posts.component';
import {ProfileDetailComponent} from './profile-detail/profile-detail.component';
import {AuthenticationService} from '../services/authentication/authentication.service';
import {User} from '../models/user';
import {UserService} from '../services/user/user.service';
import {ProfileFollowingComponent} from './profile-following/profile-following.component';
import {ProfileFollowersComponent} from './profile-followers/profile-followers.component';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

    userProfile: User;

    //TODO Other children
    @ViewChild(ProfileNameComponent)
    private profileNameChild: ProfileNameComponent;
    @ViewChild(ProfileDetailComponent)
    private profileDetailChild: ProfileDetailComponent;
    @ViewChild(ProfilePostsComponent)
    private profilePostsChild: ProfilePostsComponent;
    @ViewChild(ProfileFollowingComponent)
    private profileFollowingChild: ProfileFollowingComponent;
    @ViewChild(ProfileFollowersComponent)
    private profileFollowersChild: ProfileFollowersComponent;

    constructor(private authenticationService: AuthenticationService, private userService: UserService) {}

    ngOnInit() {
        if (!this.authenticationService.getSignedInUserId()) {
            this.authenticationService.redirectToSignIn();
        }
    }

    ngAfterViewInit() {
        //TEST TODO Remove this later
        //this.changeUser(8);
    }

    changeUser(userId: number) {
        this.userService.getUser(userId)
            .subscribe(response => {
                console.log(response);
                let user: User = response["Record"];
                this.userProfile = user;
                this.profileNameChild.changeUser(userId);
                this.profileDetailChild.changeUser(userId);
                this.profilePostsChild.changeUser(userId);
                this.profileFollowingChild.changeUser(userId);
                this.profileFollowersChild.changeUser(userId);
            },
                error => {
                    //TODO Handle this error properly
                    console.log(error);
                });
    }

    isMyProfile() {
        if (this.authenticationService.isSignedIn()) {
            this.userService.getUser(this.authenticationService.getSignedInUserId())
                .subscribe(response => {
                    console.log(response);
                    let currentSignedInUser: User = response["Record"];
                    return this.userProfile.username == currentSignedInUser.username;
                },
                    error => {
                        //TODO Handle this error properly
                        console.log(error);
                    });
        }

    }
}
