package abstractclasses;

import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import ru.yandex.qatools.htmlelements.element.HtmlElement;


public class AbstractUIElement extends HtmlElement implements WebActions, JSHelper, ActionsHelper {
}
