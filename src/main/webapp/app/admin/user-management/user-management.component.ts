import { TranslateService } from '@ngx-translate/core';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';

import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AccountService } from 'app/core/auth/account.service';
import { UserService } from 'app/core/user/user.service';
import { User } from 'app/core/user/user.model';
import { UserManagementDeleteDialogComponent } from './user-management-delete-dialog.component';
import { NzModalService } from 'ng-zorro-antd';

@Component({
  selector: 'jhi-user-mgmt',
  templateUrl: './user-management.component.html'
})
export class UserManagementComponent implements OnInit, OnDestroy {
  currentAccount: any;
  users: User[];
  error: any;
  success: any;
  userListSubscription: Subscription;
  routeData: Subscription;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  isAllDisplayDataChecked = false;
  isIndeterminate = false;
  idsChecked: { [key: string]: boolean } = {};
  listOfAllData: User[] = [];
  visibleModalExcluir = false;

  constructor(
    private userService: UserService,
    private alertService: JhiAlertService,
    private accountService: AccountService,
    private parseLinks: JhiParseLinks,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private eventManager: JhiEventManager,
    public modalService: NzModalService,
    private translateService: TranslateService
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  ngOnInit() {
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
      this.loadAll();
      this.registerChangeInUsers();
    });
  }

  ngOnDestroy() {
    this.routeData.unsubscribe();
    if (this.userListSubscription) {
      this.eventManager.destroy(this.userListSubscription);
    }
  }

  registerChangeInUsers() {
    this.userListSubscription = this.eventManager.subscribe('userListModification', () => this.loadAll());
  }

  setActive(user, isActivated) {
    user.activated = isActivated;

    this.userService.update(user).subscribe(
      () => {
        this.error = null;
        this.success = 'OK';
        this.loadAll();
      },
      () => {
        this.success = null;
        this.error = 'ERROR';
      }
    );
  }

  loadAll() {
    this.userService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<User[]>) => this.onSuccess(res.body, res.headers), (res: HttpResponse<any>) => this.onError(res.body));
  }

  trackIdentity(index, item: User) {
    return item.id;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  loadPage(page: number) {
    this.page = page;
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  transition(sort?: { key: string; value: string }) {
    if (sort) {
      this.reverse = sort.value === 'descend' ? false : true;
      this.predicate = sort.key;
    }
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  traslateString(i18n) {
    return this.translateService.get(i18n);
  }

  async deleteUser(user: User) {
    const modalRef = this.modalService.create({
      nzTitle: await this.traslateString('entity.delete.title').toPromise(),
      nzContent: UserManagementDeleteDialogComponent,
      nzComponentParams: { user },
      nzFooter: [
        {
          label: await this.traslateString('entity.action.cancel').toPromise(),
          shape: 'default',
          onClick: () => modalRef.destroy()
        },
        {
          label: await this.traslateString('entity.action.delete').toPromise(),
          type: 'primary',
          onClick: () => {
            this.userService.emitEventConfirmDelete();
            modalRef.destroy();
          }
        }
      ]
    });
  }

  private onSuccess(data, headers) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = headers.get('X-Total-Count');
    this.users = data;
  }

  private onError(error) {
    this.alertService.error(error.error, error.message, null);
  }

  checkAll(value: boolean): void {
    this.users.forEach(item => {
      if (this.currentAccount.login !== item.login) {
        this.idsChecked[item.id] = value;
      }
    });
  }

  refreshStatus(): void {
    this.isAllDisplayDataChecked = this.users.every(item => this.idsChecked[item.id]);
    this.isIndeterminate = this.users.some(item => this.idsChecked[item.id]) && !this.isAllDisplayDataChecked;
  }

  openModalDeleteChecked() {
    let aux = false;

    for (const key in this.idsChecked) {
      if (this.idsChecked[key]) {
        aux = true;
      }
    }

    if (!aux) {
      this.alertService.warning('error.notCheckedDelete');
    } else {
      this.visibleModalExcluir = true;
    }
  }

  deleteUsers() {
    const ids = [];

    for (const key in this.idsChecked) {
      if (this.idsChecked[key]) {
        ids.push(key);
      }
    }

    if (ids.length > 0) {
      this.userService.deleteMultiple(ids).subscribe(() => {
        this.loadAll();
        this.visibleModalExcluir = false;
      });
    }
  }
}
