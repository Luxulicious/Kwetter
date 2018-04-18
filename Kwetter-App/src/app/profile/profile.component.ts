import {Component, OnInit, ViewChild} from '@angular/core';
import {ProfileNameComponent} from './profile-name/profile-name.component';
import {ProfilePostsComponent} from './profile-posts/profile-posts.component';
import {ProfileDetailComponent} from './profile-detail/profile-detail.component';
import {AuthenticationService} from '../services/authentication/authentication.service';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

    //TODO Other children
    @ViewChild('profile-name')
    private profileNameChild: ProfileNameComponent;
    @ViewChild('profile-detail')
    private profileDetailChild: ProfileDetailComponent;
    @ViewChild('profile-posts')
    private profilePostsChild: ProfilePostsComponent;


    constructor(private authenticationService: AuthenticationService) {}

    ngOnInit() {
        if (!this.authenticationService.getSignedInUserId()) {
            this.authenticationService.redirectToSignIn();
        }
    }

}
