package com.rocketcommunications.views;

import com.rocketcommunications.models.Alert;
import com.rocketcommunications.models.Contact;
import com.rocketcommunications.services.DataService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@PageTitle("Main")
@Route(value = "main")
public class MainView extends VerticalLayout {
    private final String ALERT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private final String CONTACT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public MainView(DataService dataService) {
        Grid<Alert> alert = new Grid<>(Alert.class, false);
        ListDataProvider<Alert> alertDataProvider = new ListDataProvider<>(dataService.getAlerts());
        alert.setDataProvider(alertDataProvider);
        Grid.Column<Alert> errorIdColumn = alert.addColumn(Alert::getErrorId).setHeader("Id").setResizable(true).setAutoWidth(true).setSortable(true).setFooter(String.format("%s total alerts", alertDataProvider.getItems().size()));
        Grid.Column<Alert> severityColumn = alert.addColumn(Alert::getErrorSeverity).setHeader("Severity").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Alert> categoryColumn = alert.addColumn(Alert::getErrorCategory).setHeader("Category").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Alert> messageColumn = alert.addColumn(Alert::getErrorMessage).setHeader("Message").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Alert> longMessageColumn = alert.addColumn(Alert::getLongMessage).setHeader("Long Message").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Alert> timeColumn = alert.addColumn(new LocalDateTimeRenderer<>(Alert::getErrorTimeAsLocalDateTime, ALERT_FORMAT)).setComparator(Alert::getErrorTime).setHeader("Time").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Alert> selectedColumn = alert.addColumn(Alert::isSelected).setHeader("Selected").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Alert> newColumn = alert.addColumn(Alert::isNew).setHeader("New").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Alert> expandedColumn = alert.addColumn(Alert::isExpanded).setHeader("Expanded").setResizable(true).setAutoWidth(true).setSortable(true);
        alert.getStyle().set("margin-top", "8px");
        alert.getStyle().set("margin-bottom", "50px");
        alert.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        alert.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        alert.setSelectionMode(Grid.SelectionMode.NONE);
        H3 alertLabel = new H3("Alerts");
        alertLabel.getStyle().set("margin-left", "20px");
        alertLabel.getStyle().set("margin-top", "11px");
        Button alertMenuButton = new Button("Show/Hide Columns");
        alertMenuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu alertColumnToggleContextMenu = new ColumnToggleContextMenu(alertMenuButton);
        alertColumnToggleContextMenu.addAlertColumnToggleItem("Id", errorIdColumn);
        alertColumnToggleContextMenu.addAlertColumnToggleItem("Severity", severityColumn);
        alertColumnToggleContextMenu.addAlertColumnToggleItem("Category", categoryColumn);
        alertColumnToggleContextMenu.addAlertColumnToggleItem("message", messageColumn);
        alertColumnToggleContextMenu.addAlertColumnToggleItem("Long Message", longMessageColumn);
        alertColumnToggleContextMenu.addAlertColumnToggleItem("Time", timeColumn);
        alertColumnToggleContextMenu.addAlertColumnToggleItem("Selected", selectedColumn);
        alertColumnToggleContextMenu.addAlertColumnToggleItem("New", newColumn);
        alertColumnToggleContextMenu.addAlertColumnToggleItem("Expanded", expandedColumn);
        TextField alertSearch = new TextField();
        alertSearch.getStyle().set("margin-right", "20px");
        alertSearch.setWidth("290px");
        alertSearch.setPlaceholder("Search");
        alertSearch.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        alertSearch.setValueChangeMode(ValueChangeMode.EAGER);
        alertSearch.addValueChangeListener(e -> alertDataProvider.refreshAll());
        HorizontalLayout alertHeader = new HorizontalLayout(alertLabel, alertMenuButton, alertSearch);
        alertHeader.setWidthFull();
        alertHeader.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        alertHeader.setSpacing(false);
        alertHeader.setJustifyContentMode(JustifyContentMode.BETWEEN);
        alertDataProvider.addFilter(e -> {
            String searchTerm = alertSearch.getValue().trim();
            if (searchTerm.isEmpty())
                return true;
            boolean matchesErrorId = matchesTerm(e.getErrorId(), searchTerm);
            boolean matchesErrorSeverity = matchesTerm(e.getErrorSeverity(), searchTerm);
            boolean matchesErrorCategory = matchesTerm(e.getErrorCategory(), searchTerm);
            boolean matchesErrorMessage = matchesTerm(e.getErrorMessage(), searchTerm);
            boolean matchesLongMessage = matchesTerm(e.getLongMessage(), searchTerm);
            boolean matchesErrorTime = matchesTerm(e.getErrorTimeAsLocalDateTime().format(DateTimeFormatter.ofPattern(ALERT_FORMAT)), searchTerm);
            boolean matchesSelected = matchesTerm(Boolean.toString(e.isSelected()), searchTerm);
            boolean matchesNew = matchesTerm(Boolean.toString(e.isNew()), searchTerm);
            boolean matchesExpanded = matchesTerm(Boolean.toString(e.isExpanded()), searchTerm);
            return matchesErrorId || matchesErrorSeverity || matchesErrorCategory || matchesErrorMessage || matchesLongMessage || matchesErrorTime || matchesSelected || matchesNew || matchesExpanded;
        });

        Grid<Contact> contact = new Grid<>(Contact.class, false);
        ListDataProvider<Contact> contactDataProvider = new ListDataProvider<>(dataService.getContacts());
        contact.setDataProvider(contactDataProvider);
        Grid.Column<Contact> idColumn = contact.addColumn(Contact::getContactId).setHeader("Id").setResizable(true).setAutoWidth(true).setSortable(true).setFooter(String.format("%s total contacts", contactDataProvider.getItems().size()));
        Grid.Column<Contact> statusColumn = contact.addColumn(Contact::getContactStatus).setHeader("Status").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Contact> nameColumn = contact.addColumn(Contact::getContactName).setHeader("Name").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Contact> groundColumn = contact.addColumn(Contact::getContactGround).setHeader("Ground").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Contact> satelliteColumn = contact.addColumn(Contact::getContactSatellite).setHeader("Satellite").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Contact> equipmentColumn = contact.addColumn(Contact::getContactEquipment).setHeader("Equipment").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Contact> stateColumn = contact.addColumn(Contact::getContactState).setHeader("State").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Contact> stepColumn = contact.addColumn(Contact::getContactStep).setHeader("Step").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Contact> detailColumn = contact.addColumn(Contact::getContactDetail).setHeader("Detail").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Contact> beginColumn = contact.addColumn(new LocalDateTimeRenderer<>(Contact::getBeginTimestampAsLocalDateTime, CONTACT_FORMAT)).setComparator(Contact::getContactBeginTimestamp).setHeader("Begin").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Contact> endColumn = contact.addColumn(new LocalDateTimeRenderer<>(Contact::getEndTimestampAsLocalDateTime, CONTACT_FORMAT)).setComparator(Contact::getContactEndTimestamp).setHeader("End").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Contact> latitudeColumn = contact.addColumn(Contact::getContactLatitude).setHeader("Latitude").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Contact> longitudeColumn = contact.addColumn(Contact::getContactLongitude).setHeader("Longitude").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Contact> azimuthColumn = contact.addColumn(Contact::getContactAzimuth).setHeader("Azimuth").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Contact> elevationColumn = contact.addColumn(Contact::getContactElevation).setHeader("Elevation").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Contact> resolutionColumn = contact.addColumn(Contact::getContactResolution).setHeader("Resolution").setResizable(true).setAutoWidth(true).setSortable(true);
        Grid.Column<Contact> resolutionStatusColumn = contact.addColumn(Contact::getContactResolutionStatus).setHeader("Resolution Status").setResizable(true).setAutoWidth(true).setSortable(true);
        contact.getStyle().set("margin-top", "8px");
        contact.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        contact.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        contact.setSelectionMode(Grid.SelectionMode.NONE);
        H3 contactLabel = new H3("Contacts");
        contactLabel.getStyle().set("margin-left", "20px");
        contactLabel.getStyle().set("margin-top", "11px");
        Button contactMenuButton = new Button("Show/Hide Columns");
        contactMenuButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        ColumnToggleContextMenu columnToggleContextMenu = new ColumnToggleContextMenu(contactMenuButton);
        columnToggleContextMenu.addContactColumnToggleItem("Id", idColumn);
        columnToggleContextMenu.addContactColumnToggleItem("Status", statusColumn);
        columnToggleContextMenu.addContactColumnToggleItem("Name", nameColumn);
        columnToggleContextMenu.addContactColumnToggleItem("Ground", groundColumn);
        columnToggleContextMenu.addContactColumnToggleItem("Satellite", satelliteColumn);
        columnToggleContextMenu.addContactColumnToggleItem("Equipment", equipmentColumn);
        columnToggleContextMenu.addContactColumnToggleItem("State", stateColumn);
        columnToggleContextMenu.addContactColumnToggleItem("Step", stepColumn);
        columnToggleContextMenu.addContactColumnToggleItem("Detail", detailColumn);
        columnToggleContextMenu.addContactColumnToggleItem("Begin", beginColumn);
        columnToggleContextMenu.addContactColumnToggleItem("End", endColumn);
        columnToggleContextMenu.addContactColumnToggleItem("Latitude", latitudeColumn);
        columnToggleContextMenu.addContactColumnToggleItem("Longitude", longitudeColumn);
        columnToggleContextMenu.addContactColumnToggleItem("Azimuth", azimuthColumn);
        columnToggleContextMenu.addContactColumnToggleItem("Elevation", elevationColumn);
        columnToggleContextMenu.addContactColumnToggleItem("Resolution", resolutionColumn);
        columnToggleContextMenu.addContactColumnToggleItem("Resolution Status", resolutionStatusColumn);
        TextField contactSearch = new TextField();
        contactSearch.getStyle().set("margin-right", "20px");
        contactSearch.setWidth("290px");
        contactSearch.setPlaceholder("Search");
        contactSearch.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        contactSearch.setValueChangeMode(ValueChangeMode.EAGER);
        contactSearch.addValueChangeListener(e -> contactDataProvider.refreshAll());
        HorizontalLayout contactHeader = new HorizontalLayout(contactLabel, contactMenuButton, contactSearch);
        contactHeader.setWidthFull();
        contactHeader.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        contactHeader.setSpacing(false);
        contactHeader.setJustifyContentMode(JustifyContentMode.BETWEEN);
        contactDataProvider.addFilter(e -> {
            String searchTerm = contactSearch.getValue().trim();
            if (searchTerm.isEmpty())
                return true;
            boolean matchesId = matchesTerm(e.getContactId(), searchTerm);
            boolean matchesStatus = matchesTerm(e.getContactStatus(), searchTerm);
            boolean matchesName = matchesTerm(Integer.toString(e.getContactName()), searchTerm);
            boolean matchesGround = matchesTerm(e.getContactGround(), searchTerm);
            boolean matchesSatellite = matchesTerm(e.getContactSatellite(), searchTerm);
            boolean matchesEquipment = matchesTerm(e.getContactEquipment(), searchTerm);
            boolean matchesState = matchesTerm(e.getContactState(), searchTerm);
            boolean matchesStep = matchesTerm(e.getContactStep(), searchTerm);
            boolean matchesDetail = matchesTerm(e.getContactDetail(), searchTerm);
            boolean matchesBeginTimestamp = matchesTerm(e.getBeginTimestampAsLocalDateTime().format(DateTimeFormatter.ofPattern(CONTACT_FORMAT)), searchTerm);
            boolean matchesEndTimestamp = matchesTerm(e.getEndTimestampAsLocalDateTime().format(DateTimeFormatter.ofPattern(CONTACT_FORMAT)), searchTerm);
            boolean matchesLatitude = matchesTerm(Double.toString(e.getContactLatitude()), searchTerm);
            boolean matchesLongitude = matchesTerm(Double.toString(e.getContactLongitude()), searchTerm);
            boolean matchesAzimuth = matchesTerm(Double.toString(e.getContactAzimuth()), searchTerm);
            boolean matchesElevation = matchesTerm(Double.toString(e.getContactElevation()), searchTerm);
            boolean matchesResolution = matchesTerm(e.getContactResolution(), searchTerm);
            boolean matchesResolutionStatus = matchesTerm(e.getContactResolutionStatus(), searchTerm);
            return matchesId || matchesStatus || matchesName || matchesGround || matchesSatellite || matchesEquipment || matchesState || matchesStep || matchesDetail || matchesBeginTimestamp || matchesEndTimestamp || matchesLatitude || matchesLongitude || matchesAzimuth || matchesElevation || matchesResolution || matchesResolutionStatus;
        });
        add(alertHeader, alert, contactHeader,contact);
        setSizeFull();
        getElement().getThemeList().add("dark");
    }

    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
    }

    private static class ColumnToggleContextMenu extends ContextMenu {
        public ColumnToggleContextMenu(Component target) {
            super(target);
            setOpenOnClick(true);
        }

        void addAlertColumnToggleItem(String label, Grid.Column<Alert> column) {
            MenuItem menuItem = this.addItem(label, e -> column.setVisible(e.getSource().isChecked()));
            menuItem.setCheckable(true);
            menuItem.setChecked(column.isVisible());
        }

        void addContactColumnToggleItem(String label, Grid.Column<Contact> column) {
            MenuItem menuItem = this.addItem(label, e -> column.setVisible(e.getSource().isChecked()));
            menuItem.setCheckable(true);
            menuItem.setChecked(column.isVisible());
        }
    }
}
