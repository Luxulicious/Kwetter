import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatDialogModule} from '@angular/material/dialog';


@NgModule({
    imports: [MatDialogModule, MatInputModule, MatButtonModule, MatToolbarModule, MatSidenavModule, MatFormFieldModule],
    exports: [MatDialogModule, MatInputModule, MatButtonModule, MatToolbarModule, MatSidenavModule, MatFormFieldModule]
})

export class MaterialModule {}