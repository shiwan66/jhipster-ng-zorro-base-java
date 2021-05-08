import { NgModule } from '@angular/core';
import { IconDefinition } from '@ant-design/icons-angular';
import { NzIconModule, NZ_ICON_DEFAULT_TWOTONE_COLOR, NZ_ICONS } from 'ng-zorro-antd/icon';
import {
  MenuFoldOutline,
  MenuUnfoldOutline,
  BarChartOutline,
  UserAddOutline,
  UnorderedListOutline,
  UserOutline,
  DashboardOutline,
  HeartOutline,
  AlertOutline,
  BellOutline,
  CodeOutline,
  BookOutline,
  FlagOutline,
  ToolOutline,
  LockOutline,
  LogoutOutline,
  DatabaseOutline,
  PlusOutline,
  EyeOutline,
  EditOutline,
  DeleteOutline,
  ArrowLeftOutline,
  StopOutline,
  SaveOutline,
  FilterOutline,
  DownloadOutline
} from '@ant-design/icons-angular/icons';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
const icons: IconDefinition[] = [
  MenuFoldOutline,
  MenuUnfoldOutline,
  BarChartOutline,
  UserAddOutline,
  UnorderedListOutline,
  UserOutline,
  DashboardOutline,
  HeartOutline,
  AlertOutline,
  BellOutline,
  CodeOutline,
  BookOutline,
  FlagOutline,
  ToolOutline,
  LockOutline,
  LogoutOutline,
  DatabaseOutline,
  PlusOutline,
  EyeOutline,
  EditOutline,
  DeleteOutline,
  ArrowLeftOutline,
  StopOutline,
  SaveOutline,
  FilterOutline,
  DownloadOutline
];

@NgModule({
  imports: [NzIconModule],
  exports: [NzIconModule],
  providers: [{ provide: NZ_ICON_DEFAULT_TWOTONE_COLOR, useValue: '#00ff00' }, { provide: NZ_ICONS, useValue: icons }]
})
export class NzIconDefinitionModule {}
