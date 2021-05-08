import { Injectable } from '@angular/core';

import { JhiLoginModalComponent } from 'app/shared/login/login.component';

@Injectable({ providedIn: 'root' })
export class LoginModalService {
  private isOpen = false;
  constructor() {}

  open() {
    if (this.isOpen) {
      return;
    }
    this.isOpen = true;
    // const modalRef = this.modalService.open(JhiLoginModalComponent);
    // modalRef.result.finally(() => (this.isOpen = false));
    // return modalRef;
  }
}
