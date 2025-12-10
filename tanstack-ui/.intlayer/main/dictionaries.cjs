const _oznGBHUs1WhrM58Adfwi = require('../dictionary/auth.json');
const _MTONhdyx2JiJC2mx1DnK = require('../dictionary/boats.json');
const _TEHv9Z7HLCTIAmd98ZIz = require('../dictionary/components.json');
const _Mgwi6qiVdvf4HScADHcB = require('../dictionary/hulldata.json');
const _TfEFoCkX41W8lgWCAuqg = require('../dictionary/i9events.json');
const _wKZi993dq855JPkkBd1I = require('../dictionary/inspections.json');
const _cOS5lSnyVoo2QL81GmPs = require('../dictionary/rigdata.json');
const _EwKCbkKEtT5Tezdjuz61 = require('../dictionary/routes.json');

const dictionaries = {
  "auth": _oznGBHUs1WhrM58Adfwi,
  "boats": _MTONhdyx2JiJC2mx1DnK,
  "components": _TEHv9Z7HLCTIAmd98ZIz,
  "hulldata": _Mgwi6qiVdvf4HScADHcB,
  "i9events": _TfEFoCkX41W8lgWCAuqg,
  "inspections": _wKZi993dq855JPkkBd1I,
  "rigdata": _cOS5lSnyVoo2QL81GmPs,
  "routes": _EwKCbkKEtT5Tezdjuz61
};
const getDictionaries = () => dictionaries;

module.exports.getDictionaries = getDictionaries;
module.exports = dictionaries;
