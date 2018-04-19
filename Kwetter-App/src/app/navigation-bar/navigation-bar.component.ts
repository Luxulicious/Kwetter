import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from '../services/authentication/authentication.service';

@Component({
    selector: 'app-navigation-bar',
    templateUrl: './navigation-bar.component.html',
    styleUrls: ['./navigation-bar.component.css']
})
export class NavigationBarComponent implements OnInit {

    constructor(private router: Router, private authenticationService: AuthenticationService) {}

    ngOnInit() {
    }

    redirectToHome() {
        this.router.navigate(['home']);
    }

    redirectToProfile() {
        this.router.navigate(['profile']);
    }

    isSignedIn() {
        return this.authenticationService.isSignedIn();
    }

    signOut() {
        this.authenticationService.signOut();
    }
}
