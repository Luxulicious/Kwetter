import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {MaterialModule} from './material.module';

import {AppComponent} from './app.component';
import {ProfileComponent} from './profile/profile.component';
import {HomeComponent} from './home/home.component';
import {AppRoutingModule} from './/app-routing.module';
import {ProfileNameComponent} from './profile-name/profile-name.component';
import {AuthenticationComponent} from './authentication/authentication.component';
import {SignInComponent} from './authentication/sign-in/sign-in.component';
import {SignUpComponent} from './authentication/sign-up/sign-up.component';


@NgModule({
    declarations: [
        AppComponent,
        ProfileComponent,
        HomeComponent,
        ProfileNameComponent,
        AuthenticationComponent,
        SignInComponent,
        SignUpComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MaterialModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {}
