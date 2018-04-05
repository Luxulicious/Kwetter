import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProfileComponent} from './profile/profile.component';
import {HomeComponent} from './home/home.component';
import {SignInComponent} from './authentication/sign-in/sign-in.component';
import {AuthenticationComponent} from './authentication/authentication.component';
import {SignUpComponent} from './authentication/sign-up/sign-up.component';



const routes: Routes = [
    {path: 'profile', component: ProfileComponent},
    {path: 'home', component: HomeComponent},
    {
        path: 'sign-up', component: AuthenticationComponent,
        children: [{path: '', component: SignUpComponent}]
    },
    {
        path: 'sign-in', component: AuthenticationComponent,
        children: [{path: '', component: SignInComponent}]
    },
    { path: '', redirectTo:'/sign-in', pathMatch: 'full'}

];

@NgModule({
    exports: [RouterModule],
    imports: [RouterModule.forRoot(routes)]
})
export class AppRoutingModule {

}
