import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Coordinate} from '../../_models/coordinate';

@Component({
  selector: 'app-home',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css']
})
export class AlertComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<AlertComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit() {
  }

  onNoClick() {
    this.dialogRef.close();
  }

  returnCoordinate(): Coordinate {
    // REMINDER: change the coordinates with the address name
    return this.data;
  }
}
