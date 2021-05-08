import { Component, Input, EventEmitter, OnDestroy } from '@angular/core';
import { JhiEventManager } from 'ng-jhipster';

import { User } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-user-mgmt-delete-dialog',
  templateUrl: './user-management-delete-dialog.component.html'
})
export class UserManagementDeleteDialogComponent implements OnDestroy {
  @Input() user: User;
  eventSubscribe: any;

  constructor(private userService: UserService, private eventManager: JhiEventManager) {
    this.eventSubscribe = this.userService.eventConfirmDelete.subscribe(() => this.confirmDelete());
  }

  confirmDelete() {
    this.userService.delete(this.user.login).subscribe(() => {
      this.eventManager.broadcast({ name: 'userListModification', content: 'Deleted a user' });
    });
  }

  ngOnDestroy(): void {
    this.eventSubscribe.unsubscribe();
  }
}
