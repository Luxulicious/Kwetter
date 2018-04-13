import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../services/authentication/authentication.service';

@Component({
    selector: 'app-sign-in',
    templateUrl: './sign-in.component.html',
    styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {

    email: string = "";
    password: string = "";

    constructor(private authenticationService: AuthenticationService) {}

    ngOnInit() {
        //TODO Redirect to home if already logged in
    }

    signIn(): any {
        this.authenticationService.signIn(this.email, this.password);
    }
}
