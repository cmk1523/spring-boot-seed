"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var moment = require("moment");
var MillisecondToDate = MillisecondToDate_1 = (function () {
    function MillisecondToDate() {
    }
    MillisecondToDate.prototype.transform = function (value, format) {
        if (format === void 0) { format = MillisecondToDate_1.DDMMMYYYY; }
        if (format === MillisecondToDate_1.DDMMMYYYY_HH_MM_SS_Z) {
            return moment(value).utc().format(format).toUpperCase().replace(' +00:00', ' Z');
        }
        else if (format === MillisecondToDate_1.FROM_NOW) {
            return moment(value).fromNow();
        }
        else {
            return moment(value).format(format).toUpperCase();
        }
    };
    return MillisecondToDate;
}());
MillisecondToDate.DDMMMYYYY = 'DDMMMYYYY';
MillisecondToDate.DDMMMYYYY_HH_MM_SS = 'DDMMMYYYY HH:mm:ss';
MillisecondToDate.DDMMMYYYY_HH_MM_SS_Z = 'DDMMMYYYY HH:mm:ss Z';
MillisecondToDate.FROM_NOW = 'FROM_NOW';
MillisecondToDate = MillisecondToDate_1 = __decorate([
    core_1.Pipe({
        name: 'millisecondToDate'
    })
], MillisecondToDate);
exports.MillisecondToDate = MillisecondToDate;
var MillisecondToDate_1;
//# sourceMappingURL=MillisecondToDate.pipe.js.map