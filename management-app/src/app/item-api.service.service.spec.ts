import { TestBed } from '@angular/core/testing';

import { ItemApi.ServiceService } from './item-api.service.service';

describe('ItemApi.ServiceService', () => {
  let service: ItemApi.ServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ItemApi.ServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
