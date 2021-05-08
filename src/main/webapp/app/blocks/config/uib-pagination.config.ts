import { Injectable } from '@angular/core';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

@Injectable({ providedIn: 'root' })
export class PaginationConfig {
  constructor(config) {
    config.boundaryLinks = true;
    config.maxSize = 5;
    config.pageSize = ITEMS_PER_PAGE;
    config.size = 'sm';
  }
}
