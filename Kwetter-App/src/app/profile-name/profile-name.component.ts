import {Component, OnInit} from '@angular/core';
import {User} from '../models/user';

@Component({
    selector: 'app-profile-name',
    templateUrl: './profile-name.component.html',
    styleUrls: ['./profile-name.component.css']
})
export class ProfileNameComponent implements OnInit {

    //TODO Use an actual user not mock and move this variable somewhere else...
    public loggedInUser: User = new User();

    constructor() {
        this.loggedInUser.username = "Luxulicious";
        this.loggedInUser.icon = "Luxulicious.png"
    }

    ngOnInit() {
    }
}
