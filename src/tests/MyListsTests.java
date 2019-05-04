package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase
{
    private static final String name_of_folder = "Learning programming";

    @Test
    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyListToNewFolder(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
            ArticlePageObject.cancelAuthSuggestionPopup();
        }
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        if(Platform.getInstance().isAndroid()){
            MyListsPageObject.openFolderByName(name_of_folder);
        }
        MyListsPageObject.swipeByArticleToDelete(article_title);

    }

    @Test
    public void testTwoArticlesSaveBothAndDeleteOne()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line1 = "Java";
        SearchPageObject.typeSearchLine(search_line1);
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String first_article_name = ArticlePageObject.getArticleTitle();
        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyListToNewFolder(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
            ArticlePageObject.cancelAuthSuggestionPopup();
        }
        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        if(Platform.getInstance().isIOS()){
            SearchPageObject.clearSearchInput();
        }
        String search_line2 = "JavaScript";
        SearchPageObject.typeSearchLine(search_line2);
        SearchPageObject.clickByArticleWithSubstring("Programming language");
        String second_article_name = ArticlePageObject.getArticleTitle();
        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyListToNewFolder(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();
        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        if(Platform.getInstance().isAndroid()){
            MyListsPageObject.openFolderByName(name_of_folder);
        }
        MyListsPageObject.swipeByArticleToDelete(first_article_name);

        SearchPageObject.clickByArticleWithSubstring(search_line2);
        String actual_result = ArticlePageObject.getArticleTitle();
        assertEquals(
                "Article title does not match the saved article's one",
                second_article_name,
                actual_result
        );

    }
}
