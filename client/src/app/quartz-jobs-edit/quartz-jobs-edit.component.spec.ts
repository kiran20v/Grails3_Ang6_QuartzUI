import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuartzJobsEditComponent } from './quartz-jobs-edit.component';

describe('QuartzJobsEditComponent', () => {
  let component: QuartzJobsEditComponent;
  let fixture: ComponentFixture<QuartzJobsEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuartzJobsEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuartzJobsEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
