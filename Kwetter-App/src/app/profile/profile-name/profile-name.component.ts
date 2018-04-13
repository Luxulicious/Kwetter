import {Component, OnInit, Input} from '@angular/core';
import {User} from '../../models/user';
import {UserService} from '../../services/user/user.service';
import {AuthenticationService} from '../../services/authentication/authentication.service';

@Component({
    selector: 'app-profile-name',
    templateUrl: './profile-name.component.html',
    styleUrls: ['./profile-name.component.css']
})
export class ProfileNameComponent implements OnInit {

    @Input() user: User = null;

    constructor(private userService: UserService,
        private authService: AuthenticationService) {
    }

    ngOnInit() {
        if (this.user == null) {
            let userId = this.authService.getCurrentUserId();
            if (userId) {
                this.fetchUser(userId);
            } else {
                //TODO Go to log-in page
            }
        }
    }

    //TODO Make the user displayed changable via URL parameter
    changeUser(userId: number) {
        if (userId) {
            this.fetchUser(userId);
        }
    }

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

