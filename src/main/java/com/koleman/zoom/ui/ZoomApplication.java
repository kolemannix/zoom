package com.koleman.zoom.ui;

import com.koleman.zoom.business.service.WorldService;
import com.vaadin.ui.Window;
import org.dellroad.stuff.vaadin.SpringContextApplication;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ConfigurableWebApplicationContext;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class ZoomApplication extends SpringContextApplication implements BeanFactoryAware {

    BeanFactory beanFactory;

    @Autowired
    ZoomLayout zoomLayout;

    @Autowired
    WorldService service;

    @Override
    protected void initSpringApplication(ConfigurableWebApplicationContext configurableWebApplicationContext) {
        setTheme("main");
        Window mainWindow = new Window("Zoom Logic");
        mainWindow.addComponent(zoomLayout);

        setMainWindow(mainWindow);
    }

    /**
     * Callback that supplies the owning factory to a bean instance.
     * <p>Invoked after the population of normal bean properties
     * but before an initialization callback such as
     * {@link org.springframework.beans.factory.InitializingBean#afterPropertiesSet()} or a custom init-method.
     *
     * @param beanFactory owning BeanFactory (never <code>null</code>).
     *                    The bean can immediately call methods on the factory.
     * @throws org.springframework.beans.BeansException
     *          in case of initialization errors
     * @see org.springframework.beans.factory.BeanInitializationException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * Close this instance.
     * <p/>
     * <p>
     * The implementation in {@link org.dellroad.stuff.vaadin.ContextApplication} first delegates to the superclass and then
     * notifies any registered {@link org.dellroad.stuff.vaadin.ContextApplication.CloseListener}s.
     * </p>
     */
    @Override
    public void close() {
        super.close();
    }
}
