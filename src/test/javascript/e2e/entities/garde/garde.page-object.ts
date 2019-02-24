import { element, by, ElementFinder } from 'protractor';

export class GardeComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-garde div table .btn-danger'));
    title = element.all(by.css('jhi-garde div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class GardeUpdatePage {
    pageTitle = element(by.id('jhi-garde-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dateInput = element(by.id('field_date'));
    amountInput = element(by.id('field_amount'));
    seller_residantInput = element(by.id('field_seller_residant'));
    buyer_residentInput = element(by.id('field_buyer_resident'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setDateInput(date) {
        await this.dateInput.sendKeys(date);
    }

    async getDateInput() {
        return this.dateInput.getAttribute('value');
    }

    async setAmountInput(amount) {
        await this.amountInput.sendKeys(amount);
    }

    async getAmountInput() {
        return this.amountInput.getAttribute('value');
    }

    async setSeller_residantInput(seller_residant) {
        await this.seller_residantInput.sendKeys(seller_residant);
    }

    async getSeller_residantInput() {
        return this.seller_residantInput.getAttribute('value');
    }

    async setBuyer_residentInput(buyer_resident) {
        await this.buyer_residentInput.sendKeys(buyer_resident);
    }

    async getBuyer_residentInput() {
        return this.buyer_residentInput.getAttribute('value');
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class GardeDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-garde-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-garde'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
