import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {MaterialModule} from './material.module';

import {AppComponent} from './app.component';
import {ProfileComponent} from './profile/profile.component';
import {HomeComponent} from './home/home.component';
import {AppRoutingModule} from './/app-routing.module';

import {AuthenticationComponent} from './authentication/authentication.component';
import {SignInComponent} from './authentication/sign-in/sign-in.component';
import {SignUpComponent} from './authentication/sign-up/sign-up.component';
import {ProfileNameComponent} from './profile/profile-name/profile-name.component';
import {AuthenticationService} from './services/authentication/authentication.service';
import {UserService} from './services/user/user.service';
import {ApiService} from './services/api/api.service';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {ProfileDetailComponent} from './profile/profile-detail/profile-detail.component';

@NgModule({
    declarations: [
        AppComponent,
        ProfileComponent,
        HomeComponent,
        ProfileNameComponent,
        AuthenticationComponent,
        SignInComponent,
        SignUpComponent,
        ProfileDetailComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        BrowserAnimationsModule,
        MaterialModule,
        FormsModule
    ],
    providers: [UserService, AuthenticationService, ApiService],
    bootstrap: [AppComponent]
})
export class AppModule {}
