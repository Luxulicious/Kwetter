import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProfilePageComponent as ProfilePage} from './profile-page/profile-page.component';
import {HomePageComponent as HomePage} from './home-page/home-page.component';

const routes: Routes = [
    {path: 'profile', component: ProfilePage},
    {path: 'home', component: HomePage}
];

@NgModule({
    exports: [RouterModule],
    imports: [RouterModule.forRoot(routes)]
})
export class AppRoutingModule {

}
