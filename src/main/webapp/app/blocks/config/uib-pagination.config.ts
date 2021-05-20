import { Injectable } from '@angular/core';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';

export class PaginationConfig {
  constructor(config: any) {
    config.boundaryLinks = true;
    config.maxSize = 5;
    config.pageSize = ITEMS_PER_PAGE;
    config.size = 'sm';
  }
}
