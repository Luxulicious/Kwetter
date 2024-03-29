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
import {SocketService} from './services/socket/socket.service';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {ProfileDetailComponent} from './profile/profile-detail/profile-detail.component';
import {ProfilePostsComponent} from './profile/profile-posts/profile-posts.component';
import {PostService} from './services/post/post.service';
import {ProfileFollowersComponent} from './profile/profile-followers/profile-followers.component';
import {ProfileFollowingComponent} from './profile/profile-following/profile-following.component';
import {NavigationBarComponent} from './navigation-bar/navigation-bar.component';
import {CreatePostComponent} from './home/create-post/create-post.component';
import {TimelineComponent} from './home/timeline/timeline.component';

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
        ProfilePostsComponent,
        ProfileFollowersComponent,
        ProfileFollowingComponent,
        NavigationBarComponent,
        CreatePostComponent,
        TimelineComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        BrowserAnimationsModule,
        MaterialModule,
        FormsModule
    ],
    providers: [UserService, AuthenticationService, ApiService, PostService, SocketService],
    bootstrap: [AppComponent]
})
export class AppModule {}
