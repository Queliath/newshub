import angular from 'angular';
import ngCookies from 'angular-cookies';
import uirouter from 'angular-ui-router';
import pascalprechttranslate from 'angular-translate';
import uibootstrap from 'angular-ui-bootstrap';

import translate from './i18n';
import routing from './routing';

import newsService from './common/service/news.service';
import authorService from './common/service/author.service';
import tagService from './common/service/tag.service';
import commentService from './common/service/comment.service';
import translateService from './common/service/translate.service';
import errorMessageService from './common/service/error-message.service';
import modalService from './common/service/modal.service';
import nhDatetimeShortFilter from './common/filter/nh-datetime-short.filter';
import nhDatetimeFullFilter from './common/filter/nh-datetime-full.filter';

import {nhHeaderComponent} from './nh-header/nh-header.component';
import {nhAddNewsModalComponent} from './nh-add-news-modal/nh-add-news-modal.component';
import {nhDeleteNewsModalComponent} from './nh-delete-news-modal/nh-delete-news-modal.component';
import {nhNewsComponent} from './nh-news/nh-news.component';
import {nhNewsCommentsComponent} from './nh-news-comments/nh-news-comments.component'
import {nhEditNewsModalComponent} from './nh-edit-news-modal/nh-edit-news-modal.component';
import {nhNewsListComponent} from './nh-news-list/nh-news-list.component';
import {nhNewsListPaginationComponent} from './nh-news-list-pagination/nh-news-list-pagination.component';
import {nhNewsListSidebarComponent} from './nh-news-list-sidebar/nh-news-list-sidebar.component';
import {nhBackButtonComponent} from './nh-back-button/nh-back-button.component';
import {nhAddCommentPanelComponent} from './nh-add-comment-panel/nh-add-comment-panel.component';
import {nhEditCommentModalComponent} from './nh-edit-comment-modal/nh-edit-comment-modal.component';
import {nhDeleteCommentModalComponent} from './nh-delete-comment-modal/nh-delete-comment-modal.component';
import {nhAddAuthorModalComponent} from './nh-add-author-modal/nh-add-author-modal.component';
import {nhAddTagModalComponent} from './nh-add-tag-modal/nh-add-tag-modal.component';

import nhDirective from './common/directive/nh-directive';
import nhDirectiveWithScope from './common/directive/nh-directive-with-scope';

angular.module("app", [ngCookies, uirouter, pascalprechttranslate, uibootstrap])
    .config(routing)
    .config(translate)
    .service("newsService", newsService)
    .service("authorService", authorService)
    .service("tagService", tagService)
    .service("commentService", commentService)
    .service("translateService", translateService)
    .service("errorMessageService", errorMessageService)
    .service("modalService", modalService)
    .filter("nhDatetimeFullFilter", nhDatetimeFullFilter)
    .filter("nhDatetimeShortFilter", nhDatetimeShortFilter)
    .component("nhHeader", nhHeaderComponent)
    .component("nhAddNewsModal", nhAddNewsModalComponent)
    .component("nhDeleteNewsModal", nhDeleteNewsModalComponent)
    .component("nhNews", nhNewsComponent)
    .component("nhNewsComments", nhNewsCommentsComponent)
    .component("nhEditNewsModal", nhEditNewsModalComponent)
    .component("nhNewsList", nhNewsListComponent)
    .component("nhNewsListPagination", nhNewsListPaginationComponent)
    .component("nhNewsListSidebar", nhNewsListSidebarComponent)
    .component("nhBackButton", nhBackButtonComponent)
    .component("nhAddCommentPanel", nhAddCommentPanelComponent)
    .component("nhEditCommentModal", nhEditCommentModalComponent)
    .component("nhDeleteCommentModal", nhDeleteCommentModalComponent)
    .component("nhAddAuthorModal", nhAddAuthorModalComponent)
    .component("nhAddTagModal", nhAddTagModalComponent)
    .directive("nhDirective", nhDirective)
    .directive("nhDirectiveWithScope", nhDirectiveWithScope);