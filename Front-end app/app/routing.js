export default function routing($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/");

    var welcomeState = {
        name: "welcome",
        url: "/?page&limit&tag&author",
        component: "nhNewsList",
        params: {
            page: {
                value: '1',
                squash: true
            },
            limit: {
                value: '10',
                squash: true
            }
        }
    };

    var newshubState = {
        name : "newshub",
        url : "/newshub?page&limit&tag&author",
        component : "nhNewsList",
        params : {
            page : {
                value : '1',
                squash : true
            },
            limit : {
                value : '10',
                squash: true
            }
        }
    };

    var newsState = {
        name : "news",
        url : "/news/{newsId}",
        component : "nhNews",
        resolve : {
            news: function (newsService, $stateParams) {
                return newsService.getNewsById($stateParams.newsId);
            }
        },
        params : {
            prevState: {}
        }
    };

    var commentsState = {
        name : "news.comments",
        url : "/comments?page",
        component : "nhNewsComments",
        resolve : {
            response : function (commentService, $stateParams, news) {
                return commentService.getCommentsByNews(news.id, ($stateParams.page - 1) * 10, 10);
            },
            newsId : function (news) {
                return news.id;
            }
        },
        params : {
            page : {
                value : '1',
                squash : true
            }
        }
    };

    $stateProvider.state(welcomeState);
    $stateProvider.state(newshubState);
    $stateProvider.state(newsState);
    $stateProvider.state(commentsState);
}