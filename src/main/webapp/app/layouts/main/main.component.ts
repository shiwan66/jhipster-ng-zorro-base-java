import { Component, OnInit, ElementRef } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRouteSnapshot, NavigationEnd, NavigationError } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { AccountService } from 'app/core/auth/account.service';
import { ProfileService } from '../profiles/profile.service';
import { JhiLanguageHelper } from 'app/core/language/language.helper';
import { SessionStorageService } from 'ngx-webstorage';
import { JhiLanguageService } from 'ng-jhipster';
import { LoginService } from 'app/core/login/login.service';

@Component({
  selector: 'jhi-main',
  templateUrl: './main.component.html'
})
export class MainComponent implements OnInit {
  isCollapsed = false;
  inProduction = false;
  swaggerEnabled = false;
  languages: any[];
  imgUrl: string;
  constructor(
    private translateService: TranslateService,
    private titleService: Title,
    private router: Router,
    private accountService: AccountService,
    private profileService: ProfileService,
    private languageHelper: JhiLanguageHelper,
    private sessionStorage: SessionStorageService,
    private languageService: JhiLanguageService,
    private loginService: LoginService
  ) {}

  ngOnInit(): void {
    this.languages = this.languageHelper.getAll();
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateTitle();
      }
      if (event instanceof NavigationError && event.error.status === 404) {
        this.router.navigate(['/404']);
      }
    });

    this.translateService.onLangChange.subscribe(() => this.updateTitle());

    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.swaggerEnabled = profileInfo.swaggerEnabled;
    });

    this.getImageUrl();
  }

  changeLanguage(languageKey: string) {
    this.sessionStorage.store('locale', languageKey);
    this.languageService.changeLanguage(languageKey);
  }

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot): string {
    let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : '';
    if (routeSnapshot.firstChild) {
      title = this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  private updateTitle(): void {
    let pageTitle = this.getPageTitle(this.router.routerState.snapshot.root);
    if (!pageTitle) {
      pageTitle = 'global.title';
    }
    this.translateService.get(pageTitle).subscribe(title => this.titleService.setTitle(title));
  }

  isAuthenticate() {
    return this.accountService.isAuthenticated();
  }

  getNameMenu(i18n) {
    return this.translateService.get(i18n);
  }

  getImageUrl() {
    setTimeout(() => {
      this.imgUrl = this.accountService.getImageUrl();
    }, 3000);
  }

  logout() {
    this.loginService.logout();
    this.router.navigate(['']);
  }
}
