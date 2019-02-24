/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { GardeComponentsPage, GardeDeleteDialog, GardeUpdatePage } from './garde.page-object';

const expect = chai.expect;

describe('Garde e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let gardeUpdatePage: GardeUpdatePage;
    let gardeComponentsPage: GardeComponentsPage;
    let gardeDeleteDialog: GardeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Gardes', async () => {
        await navBarPage.goToEntity('garde');
        gardeComponentsPage = new GardeComponentsPage();
        expect(await gardeComponentsPage.getTitle()).to.eq('twentyOnePointsApp.garde.home.title');
    });

    it('should load create Garde page', async () => {
        await gardeComponentsPage.clickOnCreateButton();
        gardeUpdatePage = new GardeUpdatePage();
        expect(await gardeUpdatePage.getPageTitle()).to.eq('twentyOnePointsApp.garde.home.createOrEditLabel');
        await gardeUpdatePage.cancel();
    });

    it('should create and save Gardes', async () => {
        const nbButtonsBeforeCreate = await gardeComponentsPage.countDeleteButtons();

        await gardeComponentsPage.clickOnCreateButton();
        await promise.all([
            gardeUpdatePage.setDateInput('2000-12-31'),
            gardeUpdatePage.setAmountInput('5'),
            gardeUpdatePage.setSeller_residantInput('seller_residant'),
            gardeUpdatePage.setBuyer_residentInput('buyer_resident')
        ]);
        expect(await gardeUpdatePage.getDateInput()).to.eq('2000-12-31');
        expect(await gardeUpdatePage.getAmountInput()).to.eq('5');
        expect(await gardeUpdatePage.getSeller_residantInput()).to.eq('seller_residant');
        expect(await gardeUpdatePage.getBuyer_residentInput()).to.eq('buyer_resident');
        await gardeUpdatePage.save();
        expect(await gardeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await gardeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Garde', async () => {
        const nbButtonsBeforeDelete = await gardeComponentsPage.countDeleteButtons();
        await gardeComponentsPage.clickOnLastDeleteButton();

        gardeDeleteDialog = new GardeDeleteDialog();
        expect(await gardeDeleteDialog.getDialogTitle()).to.eq('twentyOnePointsApp.garde.delete.question');
        await gardeDeleteDialog.clickOnConfirmButton();

        expect(await gardeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
