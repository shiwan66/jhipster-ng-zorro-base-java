import { SpyObject } from './spyobject';
import Spy = jasmine.Spy;

export class MockActiveModal extends SpyObject {
  dismissSpy: Spy;
  closeSpy: Spy;

  constructor() {
    super();
    this.dismissSpy = this.spy('dismiss').andReturn(this);
    this.closeSpy = this.spy('close').andReturn(this);
  }
}
