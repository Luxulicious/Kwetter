import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatDialogModule} from '@angular/material/dialog';
import {MatTabsModule} from '@angular/material/tabs';
import {MatCardModule} from '@angular/material/card';
import {MatListModule} from '@angular/material/list';

@NgModule({
    imports: [MatCardModule, MatTabsModule, MatDialogModule, MatInputModule, MatButtonModule, MatToolbarModule, MatSidenavModule, MatFormFieldModule, MatListModule],
    exports: [MatCardModule, MatTabsModule, MatDialogModule, MatInputModule, MatButtonModule, MatToolbarModule, MatSidenavModule, MatFormFieldModule, MatListModule]
})

export class MaterialModule {}