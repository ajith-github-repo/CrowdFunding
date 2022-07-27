import * as dateUtil from './dateUtil';
import Constants from '../constants/constants';

describe('tests for date utilities',()=>{

    let constants;

    beforeAll(()=>{
        constants = new Constants().getConfig();
    })

    beforeEach(()=>{
        jest.restoreAllMocks();
    })

    afterEach(()=>{
        //jest.clearAllMocks();
    })
    test('getDateInFormat returns date formatted successfully',()=>{
        jest.spyOn(dateUtil,'getDateInFormat');
        const date = new Date();
        let format = 'mm-dd-yyyy';
        let actualOp = dateUtil.getDateInFormat(date,format);
        let expectedOp = '12-14-2021';
        expect(actualOp).toBe(expectedOp);
        expect(dateUtil.getDateInFormat).toHaveBeenCalledTimes(1);
        expect(dateUtil.getDateInFormat).toBeCalledWith(date,format);

    })

    test('getDayFromDate returns day successfully',()=>{
        jest.spyOn(dateUtil,'getDayFromDate');
        const date = new Date();
        let actualOp = dateUtil.getDayFromDate(date);
        let expectedOp = constants.WEEKDAY[date.getDay()];
        expect(actualOp).toBe(expectedOp);
        expect(dateUtil.getDayFromDate).toHaveBeenCalledTimes(1);
        expect(dateUtil.getDayFromDate).toBeCalledWith(date);

    })

    test('getDayFromDate returns NONE when date is N/A',()=>{
        jest.spyOn(dateUtil,'getDayFromDate');
        const date = 'N/A';
        let actualOp = dateUtil.getDayFromDate(date);
        let expectedOp = constants.WEEKDAY[7];
        expect(actualOp).toBe(expectedOp);
        expect(dateUtil.getDayFromDate).toHaveBeenCalledTimes(1);
        expect(dateUtil.getDayFromDate).toBeCalledWith(date);

    })


})