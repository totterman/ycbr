const _XhgYHxuvPpqR8xX5BfZ8 = require('../unmerged_dictionary/auth.json');
const _mXd4yRMc4uNaBcRlSbgo = require('../unmerged_dictionary/boats.json');
const _DkKSFGk6wzJVzcLVeHF6 = require('../unmerged_dictionary/components.json');
const _FwKdb4LyPuPUys6yGJJA = require('../unmerged_dictionary/hulldata.json');
const _XUh55SeP4ioqmh9lvdUh = require('../unmerged_dictionary/i9events.json');
const _GEd9Ww94nWA4XL8L4oRG = require('../unmerged_dictionary/inspections.json');
const _5ecren8e5vjGbLxr8OH1 = require('../unmerged_dictionary/rigdata.json');
const _gMCDsgVxNfsbsqz9osPs = require('../unmerged_dictionary/routes.json');

const dictionaries = {
  "auth": _XhgYHxuvPpqR8xX5BfZ8,
  "boats": _mXd4yRMc4uNaBcRlSbgo,
  "components": _DkKSFGk6wzJVzcLVeHF6,
  "hulldata": _FwKdb4LyPuPUys6yGJJA,
  "i9events": _XUh55SeP4ioqmh9lvdUh,
  "inspections": _GEd9Ww94nWA4XL8L4oRG,
  "rigdata": _5ecren8e5vjGbLxr8OH1,
  "routes": _gMCDsgVxNfsbsqz9osPs
};
const getUnmergedDictionaries = () => dictionaries;

module.exports.getUnmergedDictionaries = getUnmergedDictionaries;
module.exports = dictionaries;
