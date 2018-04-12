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
        private authService: AuthenticationService) {}

    ngOnInit() {
        if (this.user == null) {
            let userId = this.authService.getCurrentUserId();
            if (userId) {
                this.user = this.fetchUser(userId);
            } else {
                //TODO Go to log-in page
            }
        }
    }

    //TODO Make the user displayed changable via URL parameter
    changeUser(userId: number) {
        if(userId) {
            this.user = this.fetchUser(userId);
        }
    }

    fetchUser(userId: number): User {
        return this.userService.fetchUser();
//        this.userService.fetchUser(userId)
//            .subscribe(response => {
//                this.user = response;
//            },
//                error => {
//                    //TODO Handle this error properly
//                    console.log(error);
//                });
//                
    }
}

