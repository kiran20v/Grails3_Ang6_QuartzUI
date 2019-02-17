import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuartzJobsListEditComponent } from './quartz-jobs-list-edit.component';

describe('QuartzJobsListEditComponent', () => {
  let component: QuartzJobsListEditComponent;
  let fixture: ComponentFixture<QuartzJobsListEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuartzJobsListEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuartzJobsListEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
