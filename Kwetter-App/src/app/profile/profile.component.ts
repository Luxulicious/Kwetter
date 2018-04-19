import {Component, OnInit, ViewChild} from '@angular/core';
import {ProfileNameComponent} from './profile-name/profile-name.component';
import {ProfilePostsComponent} from './profile-posts/profile-posts.component';
import {ProfileDetailComponent} from './profile-detail/profile-detail.component';
import {AuthenticationService} from '../services/authentication/authentication.service';
import {User} from '../models/user';
import {UserService} from '../services/user/user.service';
import {ProfileFollowingComponent} from './profile-following/profile-following.component';
import {ProfileFollowersComponent} from './profile-followers/profile-followers.component';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Subscription';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

    userProfile: User;
    isMyProfile: boolean;
    isAlreadyFollowing: boolean;

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

    constructor(private router: Router, private authenticationService: AuthenticationService, private userService: UserService) {
    }

    ngOnInit() {
        //TODO Change this to an active route
        let route: string = this.router.routerState.snapshot.url;
        let param: number = +route.substring(route.lastIndexOf('/') + 1);
        if (param) {
            this.goToProfile(param);
        }
        else {
            this.fetchUser(this.authenticationService.getSignedInUserId());
            this.fetchIsMyProfile();
        }
    }

    ngAfterViewInit() {
    }

    goToProfile(userId: number) {
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
                this.fetchIsMyProfile();
            },
                error => {
                    //TODO Handle this error properly
                    console.log(error);
                });
    }

    fetchIsAlreadyFollowing() {
        if (this.authenticationService.isSignedIn()) {
            this.userService.getFollowing(this.authenticationService.getSignedInUserId())
                .subscribe(response => {
                    let users: User[] = response["Records"];
                    console.log(users);
                    this.isAlreadyFollowing = false;
                    for (let i = 0; i < users.length; i++) {
                        console.log(users[i].id + "   " + this.userProfile.id)
                        if (users[i].id == this.userProfile.id) {
                            this.isAlreadyFollowing = true;
                            break;
                        }
                    }
                },
                    error => {
                        //TODO Handle this error properly
                        console.log(error);
                    });
        }
        else {this.authenticationService.redirectToHome()}
    }

    fetchIsMyProfile(): void {
        if (this.authenticationService.isSignedIn()) {
            this.userService.getUser(this.authenticationService.getSignedInUserId())
                .subscribe(response => {
                    let currentSignedInUser: User = response["Record"];
                    console.log(currentSignedInUser.id + "     " + this.userProfile.id);
                    this.isMyProfile = this.userProfile.id == currentSignedInUser.id;

                    this.fetchIsAlreadyFollowing();
                },
                    error => {
                        //TODO Handle this error properly
                        console.log(error);
                    });
        }
        else {this.authenticationService.redirectToHome()}
    }

    fetchUser(userId: number): void {
        this.userService.getUser(userId)
            .subscribe(response => {
                console.log(response);
                let user: User = response["Record"];
                this.userProfile = user;
            },
                error => {
                    //TODO Handle this error properly
                    console.log(error);
                });
    }

    unfollowUser() {
        this.userService.unfollow(this.authenticationService.getSignedInUserId()
        , this.userProfile.id)
            .subscribe(response => {
                let succes: boolean = response["succes"];
                if (succes) {
                    this.fetchIsAlreadyFollowing();
                }
                else {
                    console.log("Could not unfollow.")
                }
            },
                error => {
                    //TODO Handle this error properly
                    console.log(error);
                });
    }
}
