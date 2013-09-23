package com.koleman.zoom.ui.inspector;

import com.koleman.zoom.business.event.ActiveWorldChangedEvent;
import com.koleman.zoom.business.event.TileClickedEvent;
import com.koleman.zoom.business.event.WorldUpdatedEvent;
import com.koleman.zoom.business.service.WorldService;
import com.koleman.zoom.logic.model.shape.*;
import com.koleman.zoom.logic.parse.token.Variable;
import com.koleman.zoom.ui.board.Tile;
import com.sun.org.apache.xpath.internal.operations.VariableSafeAbsRef;
import com.vaadin.ui.*;
import org.dellroad.stuff.vaadin.VaadinApplicationListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Author Koleman Nix
 * Computing ID: jkn3wn
 * Created On 6/11/13
 * Assignment: Homework
 * Other Collaborators: None
 */
@Component
public class Inspector extends Panel {

    private ShapeDetailForm shapeDetailForm;

    @Autowired
    SimpleApplicationEventMulticaster multicaster;

    @Autowired
    WorldService worldService;

    private VaadinApplicationListener<TileClickedEvent> tileClickedListener;
    private VaadinApplicationListener<WorldUpdatedEvent> worldUpdatedListener;
    private VaadinApplicationListener<ActiveWorldChangedEvent> worldChangedListener;

    private Button deleteButton;
    private Button newButton;
    private Button updateButton;
    private Button randomizeButton;

    @PostConstruct
    private void doInit() {
        setCaption("Shapes");
        setSizeUndefined();
        deleteButton = new Button("Delete", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                worldService.clearSelectedTile();
                setIsShapeSelected(false);
            }
        });
        newButton = new Button("New", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                worldService.createShape();
                setIsShapeSelected(true);
            }
        });
        updateButton = new Button("Update", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                worldService.updateShape(shapeDetailForm.generateShape());
            }
        });
        randomizeButton = new Button("Randomize", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ZoomShape randomShape = ShapeFactory.generateRandomShape();
                setShape(randomShape);
                worldService.updateShape(randomShape);
            }
        });
        shapeDetailForm = new ShapeDetailForm(this);
        addComponent(shapeDetailForm);
        HorizontalLayout buttonRow = new HorizontalLayout();
        buttonRow.addComponent(newButton);
        buttonRow.addComponent(randomizeButton);
        buttonRow.addComponent(updateButton);
        buttonRow.addComponent(deleteButton);
        newButton.setEnabled(false);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        addComponent(buttonRow);

        tileClickedListener = new VaadinApplicationListener<TileClickedEvent>(multicaster, TileClickedEvent.class) {
            @Override
            protected void onApplicationEventInternal(TileClickedEvent event) {
                setShape(event.getTile().getShape());
            }
        };
        multicaster.addApplicationListener(tileClickedListener);

        worldUpdatedListener = new VaadinApplicationListener<WorldUpdatedEvent>(multicaster, WorldUpdatedEvent.class) {
            @Override
            protected void onApplicationEventInternal(WorldUpdatedEvent event) {
                Tile selectedTile = worldService.getSelectedTile();
                if (selectedTile != null) {
                    setShape(worldService.getSelectedTile().getShape());
                }
            }
        };
        multicaster.addApplicationListener(worldUpdatedListener);

        worldChangedListener = new VaadinApplicationListener<ActiveWorldChangedEvent>(multicaster, ActiveWorldChangedEvent.class) {
            @Override
            protected void onApplicationEventInternal(ActiveWorldChangedEvent event) {
                setShape(null);
            }
        };
        multicaster.addApplicationListener(worldChangedListener);
    }

    private void setIsShapeSelected(boolean isShapeSelected) {
        updateButton.setEnabled(isShapeSelected);
        deleteButton.setEnabled(isShapeSelected);
        newButton.setEnabled(!isShapeSelected);
    }

    public void setShape(ZoomShape shape) {
        shapeDetailForm.setShape(shape);
    }

    private class ShapeDetailForm extends Form {
        private final Select shapeBox;
        private final Select sizeBox;
        private final Select colorBox;
        private final Select tagBox;

        private ZoomShape currentShape;
        private Inspector inspector;

        public ShapeDetailForm(Inspector inspector) {
            this.inspector = inspector;
            shapeBox = new Select("Shape", Arrays.asList(Type.values()));
            sizeBox = new Select("Size", Arrays.asList(com.koleman.zoom.logic.model.shape.Size.values()));
            colorBox = new Select("Color", Arrays.asList(com.koleman.zoom.logic.model.shape.Color.values()));
            tagBox = new Select("Tag", Arrays.asList(Variable.values()));
            shapeBox.setFilteringMode(Select.FILTERINGMODE_STARTSWITH);
            sizeBox.setFilteringMode(Select.FILTERINGMODE_STARTSWITH);
            colorBox.setFilteringMode(Select.FILTERINGMODE_STARTSWITH);

            addField("shape", shapeBox);
            addField("size", sizeBox);
            addField("color", colorBox);
            addField("tag", tagBox);
            currentShape = null;
            update();
        }

        public void setShape(ZoomShape shape) {
            currentShape = shape;
            update();
        }

        protected void update() {
            if (currentShape == null) {
                inspector.setIsShapeSelected(false);
                shapeBox.select(null);
                sizeBox.select(null);
                colorBox.select(null);
                tagBox.select(null);
            } else {
                inspector.setIsShapeSelected(true);
                getField("shape").setValue(currentShape.getType());
                getField("size").setValue(currentShape.getSize());
                getField("color").setValue(currentShape.getColor());
                getField("tag").setValue(currentShape.getTag());
            }
        }

        private ZoomShape generateShape() {
            Type selectedType = (Type)shapeBox.getValue();
            Size selectedSize = (Size)sizeBox.getValue();
            Color selectedColor = (Color)colorBox.getValue();
            ZoomShape newShape = ShapeFactory.createShape(selectedSize, selectedColor, selectedType);
            newShape.setTag((Variable)tagBox.getValue());
            if (!currentShape.equals(newShape)) {
                 return newShape;
            }
            return currentShape;
        }
    }
}
