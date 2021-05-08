import { Component, OnDestroy, OnInit } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-alert',
  template: `
    <div class="alerts" role="alert">
      <div *ngFor="let alert of alerts" [ngClass]="setClasses(alert)">
        <!-- <ngb-alert *ngIf="alert && alert.type && alert.msg" [type]="alert.type" (close)="alert.close(alerts)">
          <pre [innerHTML]="alert.msg"></pre>
        </ngb-alert> -->
        <nz-alert
          *ngIf="alert && alert.type && alert.msg"
          [nzType]="alert.type == 'danger' ? 'error' : alert.type"
          [nzMessage]="htmlToText(alert.msg)"
          nzShowIcon
          nzCloseable
          (nzOnClose)="alert.close(alerts)"
        ></nz-alert>
        <br />
      </div>
    </div>
  `
})
export class JhiAlertComponent implements OnInit, OnDestroy {
  alerts: any[];

  constructor(private alertService: JhiAlertService) {}

  ngOnInit() {
    this.alerts = this.alertService.get();
  }

  setClasses(alert) {
    return {
      'jhi-toast': alert.toast,
      [alert.position]: true
    };
  }

  htmlToText(html) {
    const temp = document.createElement('div');
    temp.innerHTML = html;
    return temp.textContent || temp.innerText || '';
  }

  ngOnDestroy() {
    this.alerts = [];
  }
}
