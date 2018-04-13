import { Component, OnInit, ViewChild } from '@angular/core';
import {ProfileNameComponent} from './profile-name/profile-name.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  //TODO Other children
  @ViewChild('profile-name')
  private profileNameChild: ProfileNameComponent;
    

  
  constructor() { }

  ngOnInit() {
  }

}
