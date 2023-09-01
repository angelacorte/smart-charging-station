## 1.0.0 (2023-09-01)


### Features

* add a message type for reservation result ([503f501](https://github.com/angelacorte/smart-charging-station/commit/503f501e3820e405af9c678ae130df01a3b0a714))
* add a provider actor for all charging stations ([49724fc](https://github.com/angelacorte/smart-charging-station/commit/49724fc6f76720ec7b0e6ebfb6a4a37387263105))
* add application config files ([8adc3ba](https://github.com/angelacorte/smart-charging-station/commit/8adc3bafaa4cde967aa7138c6fe394f55f7262e6))
* add battery status ([004cee1](https://github.com/angelacorte/smart-charging-station/commit/004cee16b640606e63d27f9740b150356ac4a0ee))
* add car model ([2ac6d46](https://github.com/angelacorte/smart-charging-station/commit/2ac6d4666595f7180630aecf28d6ec31ad4de9ba))
* add charging station actor ([32f05b5](https://github.com/angelacorte/smart-charging-station/commit/32f05b550c6ea0401deb30f1695826363a72f387))
* add charging station status enum and case class ([c824991](https://github.com/angelacorte/smart-charging-station/commit/c824991d1e9614cae4c1d55e82b7dec6cb8b948f))
* add ChargingStationActor class ([14f39d8](https://github.com/angelacorte/smart-charging-station/commit/14f39d846b9c5c195fd7df9f0d5ef6d69e9053c5))
* add cors handler ([4a63add](https://github.com/angelacorte/smart-charging-station/commit/4a63add5d9cade3d9e8403591ca1ac5f32c370f8))
* add cs status to logger ([00ca671](https://github.com/angelacorte/smart-charging-station/commit/00ca67133c46b439ce45acd37d9f163bf370bd25))
* add event for battery actor ([1237b69](https://github.com/angelacorte/smart-charging-station/commit/1237b69c09da2fff375d3a7a6737d7218676c609))
* add first route ([99da0b3](https://github.com/angelacorte/smart-charging-station/commit/99da0b38e096b794edbc4abdf094a557e12f69fa))
* add givens for marshalling and unmarshalling ([95eb0a4](https://github.com/angelacorte/smart-charging-station/commit/95eb0a497e610da1b7d9052812b3aa71f6968287))
* add more charging stations to the sim ([6bb4c4a](https://github.com/angelacorte/smart-charging-station/commit/6bb4c4ab5fd76ab3aef7f6d311b5f12808070394))
* add more fields to the charging station ([629e377](https://github.com/angelacorte/smart-charging-station/commit/629e3773e918efad1c9272a1c246093ca7d8a068))
* add project structure ([54327f7](https://github.com/angelacorte/smart-charging-station/commit/54327f7d1cf795d93a9b433544851ce16c147807))
* add reaction to GetChargingStations ([fc6c746](https://github.com/angelacorte/smart-charging-station/commit/fc6c746d23dd52110a606235d30f2d251aeccdbc))
* add request to start car ([ce23685](https://github.com/angelacorte/smart-charging-station/commit/ce2368521ee35d6a16203dea8117abea5434edbb))
* add reservation model entity ([9dc65b9](https://github.com/angelacorte/smart-charging-station/commit/9dc65b942e01f34dcacd2f4d7ab80059097b6197))
* add route for single charging station ([c9e7a5a](https://github.com/angelacorte/smart-charging-station/commit/c9e7a5af7f08a843420e03ce32e4b73abb4066a5))
* add status parameter in CSUpdated ([2cc1fc5](https://github.com/angelacorte/smart-charging-station/commit/2cc1fc5ff4317e7948789ce7f57a5c8083040dca))
* add status parameter in CSUpdated ([865cb21](https://github.com/angelacorte/smart-charging-station/commit/865cb2148af4acca1daa7bfddc74b0650c969fae))
* add trait for serialization ([69aaef1](https://github.com/angelacorte/smart-charging-station/commit/69aaef1a83250dbc6d7e69c985af479001a54714))
* add user app actor ([4af5a3c](https://github.com/angelacorte/smart-charging-station/commit/4af5a3ca8218183104a01f65ac4fc0704f9e7138))
* add utility extension to ChargingStation ([79c6264](https://github.com/angelacorte/smart-charging-station/commit/79c626411099b613aa25f898613a377040c59669))
* add working main ([832abf1](https://github.com/angelacorte/smart-charging-station/commit/832abf11a32db93d973770461015054614d01cc5))
* complete user app actor ([fca6504](https://github.com/angelacorte/smart-charging-station/commit/fca6504b850a6691ee97b520b97719e9f583faef))
* create an actor representing an http server ([0c87e53](https://github.com/angelacorte/smart-charging-station/commit/0c87e531c963899447f715702303aca97afd9c20))
* create charging station events object ([ea03a73](https://github.com/angelacorte/smart-charging-station/commit/ea03a7330ec19d9225c9803cc6e5f95ff432f44d))
* create user app case class ([fa6fbd0](https://github.com/angelacorte/smart-charging-station/commit/fa6fbd0b7771a6e399a0543a24f67c44d7eb5cbf))
* create user app events ([a135a50](https://github.com/angelacorte/smart-charging-station/commit/a135a50eea3d2eb3c92e6c6915c7680d1da496d0))
* implement ask state and change state messages ([b6a2842](https://github.com/angelacorte/smart-charging-station/commit/b6a2842990cda529feeb48c1aa40546542179925))
* implement car actor ([4edb00f](https://github.com/angelacorte/smart-charging-station/commit/4edb00f97260ae73312bdfbe08389bc7c5d471e4))
* implement charge request ([57c3020](https://github.com/angelacorte/smart-charging-station/commit/57c3020b3950fb55f1d1f3accc2c19665d5cee29))
* implement charging station actor ([fc2667b](https://github.com/angelacorte/smart-charging-station/commit/fc2667bdc40c0ad0ec7d20dd46a524b9e7f686b2))
* implement reservation api route ([d9e603f](https://github.com/angelacorte/smart-charging-station/commit/d9e603fec2a4723af1446be386d8baf61dad2bc3))
* implement reserve request ([8c30e57](https://github.com/angelacorte/smart-charging-station/commit/8c30e575dbef839d763464b9216840be0d2350fc))
* introduce battery as a concept and actor ([ef7c6fc](https://github.com/angelacorte/smart-charging-station/commit/ef7c6fc9e08b4a1d38e7bb65fa99c6249f269ad0))
* make charging stations listen for providers and vice versa ([fe9ee26](https://github.com/angelacorte/smart-charging-station/commit/fe9ee26aae406104e38666d0a8f28f52a19a8dfd))
* partially implement charging station actor ([80c7b5d](https://github.com/angelacorte/smart-charging-station/commit/80c7b5dab16461d43f287c8584238916aa6ea7db))


### Dependency updates

* **deps:** add akka dependency ([ad00bed](https://github.com/angelacorte/smart-charging-station/commit/ad00bed1ac4c778a59c6bf4807d1a89f45024d09))
* **deps:** add Akka HTTP and Akka Streams ([4b313a6](https://github.com/angelacorte/smart-charging-station/commit/4b313a64cd8b27791afaf4d34fa1c160a62f5f5c))
* **deps:** add dependency ([95a5033](https://github.com/angelacorte/smart-charging-station/commit/95a5033097f0c7153b68e18728ed8efefd24a209))
* **deps:** update dependencies ([785dd1a](https://github.com/angelacorte/smart-charging-station/commit/785dd1a05e3edd71b244a8ccc459afe31592d607))


### Bug Fixes

* add import ([85a241d](https://github.com/angelacorte/smart-charging-station/commit/85a241d0d11932dc289261fe52a624abcf85c5d7))
* add missing import ([f9d1650](https://github.com/angelacorte/smart-charging-station/commit/f9d1650c8a058105deb5ffa603716054396d8ad3))
* **ci:** add release job check to success ([7664c92](https://github.com/angelacorte/smart-charging-station/commit/7664c92267ff17833e5f81d5294e7c3fae74a68c))
* **deps:** add necessary dependencies ([ec9f9f7](https://github.com/angelacorte/smart-charging-station/commit/ec9f9f7fba6d2a822c7fa4bf24c08301b6cd4b1e))
* **deps:** fix broken version reference ([19b0437](https://github.com/angelacorte/smart-charging-station/commit/19b04377d532fb2ea1c42119f875227eb5a5b628))
* fix bug due to multiple service spawning by separating setup and running logic ([d6058b3](https://github.com/angelacorte/smart-charging-station/commit/d6058b33e7a8e7023d8e5c9677481097075ce15a))
* fix bug due to wrong type expected ([98592a6](https://github.com/angelacorte/smart-charging-station/commit/98592a69d4a4638d053758a5a8dcc0f426925f53))
* fix case class extension ([0f69a42](https://github.com/angelacorte/smart-charging-station/commit/0f69a4231a70e6f7ed68f46f3961f28299f1263b))
* fix imports ([9ee9f5f](https://github.com/angelacorte/smart-charging-station/commit/9ee9f5f67bfd38d61f829c1983c894f6ddb34161))
* fix messageAdapter call ([4fdf1aa](https://github.com/angelacorte/smart-charging-station/commit/4fdf1aac53deee100cdfbb536410270112a14ccb))
* fix problems and refactor signatures ([1609774](https://github.com/angelacorte/smart-charging-station/commit/160977427979032a6e1fcb27313583644c76095d))
* fix workflow ([96e1d0c](https://github.com/angelacorte/smart-charging-station/commit/96e1d0c8502f6b0529f5e7de2fe959dd73a6fb6e))
* make ChargingStation serializable with cbor ([f03c74f](https://github.com/angelacorte/smart-charging-station/commit/f03c74fb3373d709c51a1dddedf714dd19dc862a))
* make events serializable with cbor ([b082a03](https://github.com/angelacorte/smart-charging-station/commit/b082a03fce34ff9041013402848f1af7106fe4b4))
* make provider register to receptionist ([31feb97](https://github.com/angelacorte/smart-charging-station/commit/31feb976ad6c4fb7d3c76c658361ee92f95fd0f4))
* make server respond with appropriate status code for failures ([da28f22](https://github.com/angelacorte/smart-charging-station/commit/da28f221149b1da11cca653ab374110f8b98ce93))
* notify providers when updating ([d4ed570](https://github.com/angelacorte/smart-charging-station/commit/d4ed570c1551d3bd9a9705c51331272b111753b7))
* remove unused import ([eb64c19](https://github.com/angelacorte/smart-charging-station/commit/eb64c199a78d0fda2127062e96089db39b2abcae))
* remove useless argument in GetChargingStations ([c7f764c](https://github.com/angelacorte/smart-charging-station/commit/c7f764cc41093ea54fd96f466b5422417fd437fd))
* return explicit behavior ([fd207ac](https://github.com/angelacorte/smart-charging-station/commit/fd207acad7f4c9714496564ad19fe13337791622))
* **test:** create the charging station correctly ([0289a00](https://github.com/angelacorte/smart-charging-station/commit/0289a009d8a5cd406d01a281ba0ede356dcdeaf5))
* **test:** fix creation test ([ce98195](https://github.com/angelacorte/smart-charging-station/commit/ce98195818cf844126bf50201f0ffee97ba207f3))
* **test:** fixed and refactored unit tests ([6c02d5e](https://github.com/angelacorte/smart-charging-station/commit/6c02d5e8cd3b623438bcf9ebfb7225190c4b927a))
* **test:** remove useless test ([15c940a](https://github.com/angelacorte/smart-charging-station/commit/15c940a108a12fc2678e4c3e5b2c505933b424c8))
* update case class signature ([a50eab7](https://github.com/angelacorte/smart-charging-station/commit/a50eab7111bcf73dc144e66680a09df9b6627902))
* update the providers set upon receiving receptionist event ([5d2e29b](https://github.com/angelacorte/smart-charging-station/commit/5d2e29b8096b0105a36eafa9d4c583fe99ca0499))


### Documentation

* add comments ([c65d548](https://github.com/angelacorte/smart-charging-station/commit/c65d548489d9161e9d3616c22dd2d98f0500093a))
* add comments in ChargingStation classes ([485f3dd](https://github.com/angelacorte/smart-charging-station/commit/485f3dd0e243c22b078e0cd51d288853fdf92c25))


### Tests

* add car actor test ([6d02b2a](https://github.com/angelacorte/smart-charging-station/commit/6d02b2ade04431812584997630be1585f3a8edd2))
* add test base class ([555930b](https://github.com/angelacorte/smart-charging-station/commit/555930be66f71ee84d31414be71d1fcb6b61ab94))
* add test for charging response and refactoring ([867e42b](https://github.com/angelacorte/smart-charging-station/commit/867e42b8b3711a2debf29d26ee6656031cc573e0))
* add test for charging response Ok ([36e52c1](https://github.com/angelacorte/smart-charging-station/commit/36e52c136b639918e66fe517ffc8b0ca2fa63ab2))
* add test for cs send state ([f508543](https://github.com/angelacorte/smart-charging-station/commit/f508543e16f519a70b13d3a758022e2be94d484b))
* add test for cs update state ([5bd0f40](https://github.com/angelacorte/smart-charging-station/commit/5bd0f400b0288cfe5d6e724cdb93c93ce3d8b000))
* complete charging station tests ([9a30935](https://github.com/angelacorte/smart-charging-station/commit/9a309355bac199fe9dd5746432c32ae3b2367868))
* create test class for user app ([96019c3](https://github.com/angelacorte/smart-charging-station/commit/96019c3461a298824444bbf3aca826f281e91192))
* fix parameter type in test ([592e479](https://github.com/angelacorte/smart-charging-station/commit/592e479d0b9d07af8c08a4da83c4ae6f9298b38a))
* update test style and refactor test ([345197f](https://github.com/angelacorte/smart-charging-station/commit/345197f7496703cfc80d62c7c93bca116f3bf306))


### Build and continuous integration

* add release job ([c2fee75](https://github.com/angelacorte/smart-charging-station/commit/c2fee759f537367507be3859151c4cb6236f6f74))
* remove unsupported jdks ([b4d69fe](https://github.com/angelacorte/smart-charging-station/commit/b4d69fef3841d7504b02bfa35eed7ae24aa3f66f))
* remove unsupported jdks from ci test run ([b194b28](https://github.com/angelacorte/smart-charging-station/commit/b194b285d33477325f779c1a44ee130e0df14ac4))


### General maintenance

* [skip ci] update version in build.sbt ([cb612a0](https://github.com/angelacorte/smart-charging-station/commit/cb612a0e6fb5d1a44d26280d5b90256bfb248938))
* add .gitattributes ([b182989](https://github.com/angelacorte/smart-charging-station/commit/b182989f88132a8c7e58549cc97e04ef70b81b9c))
* add import ([49b3fab](https://github.com/angelacorte/smart-charging-station/commit/49b3fab7da46764fdb33b1b24115cbc37c68e9fd))
* add more comments ([97b4446](https://github.com/angelacorte/smart-charging-station/commit/97b44468c86652bb3436a72f4bb6712dae359deb))
* add semantic release config ([f244dc6](https://github.com/angelacorte/smart-charging-station/commit/f244dc6cc2dd4306fd741af8d9ef40ba338318e9))
* add workflows ([b4f78a5](https://github.com/angelacorte/smart-charging-station/commit/b4f78a5d05bc1989b071efc52c4eccf9b75439fa))
* black magic exists? ([f47e005](https://github.com/angelacorte/smart-charging-station/commit/f47e00524b2bf63f87b02e087eab4a1d9ecfca4a))
* **config:** allow multi mbeans in same jvm ([bd609f6](https://github.com/angelacorte/smart-charging-station/commit/bd609f67ab3291aabcf40c134a206693a2954fa9))
* extend charging time ([b09df2d](https://github.com/angelacorte/smart-charging-station/commit/b09df2dab975fe75ca7e287f7d0486c7ada567c4))
* go back to old version ([386afe1](https://github.com/angelacorte/smart-charging-station/commit/386afe1a5bdd9f8fc83c9499647955b43938d3f6))
* optimize import ([f0dcfce](https://github.com/angelacorte/smart-charging-station/commit/f0dcfce8ee3f01931b7477a0987aaa8b28e767be))
* optimize imports ([fb9b6b8](https://github.com/angelacorte/smart-charging-station/commit/fb9b6b850576af89b40e620e9127af2406949ee2))
* optimize imports ([7149641](https://github.com/angelacorte/smart-charging-station/commit/7149641238341235ae62a06c05af6c584102bf43))
* optimize imports ([398f8c1](https://github.com/angelacorte/smart-charging-station/commit/398f8c1715705a274960c94b8a137125a599518e))
* optimize imports and refactor cors application ([c999e58](https://github.com/angelacorte/smart-charging-station/commit/c999e5819a928726d21ca972eb91e140a0172f0f))
* remove comment ([e20a1a4](https://github.com/angelacorte/smart-charging-station/commit/e20a1a46413b0956970639eaee37447dc83be5ed))
* remove unused class ([89f3b4f](https://github.com/angelacorte/smart-charging-station/commit/89f3b4f38f49ba20a7e2d829c9ac30e26ebdadca))
* remove unused messages ([3bb544f](https://github.com/angelacorte/smart-charging-station/commit/3bb544fa7ecce99875618dd6a58367616e4c530a))
* remove useless check ([db045fe](https://github.com/angelacorte/smart-charging-station/commit/db045fed11b1154275423ce4b9f2190044d87dab))
* remove useless concepts ([169ada9](https://github.com/angelacorte/smart-charging-station/commit/169ada90353bcbd70d512b0ea04d4e366935dd3c))
* remove useless concepts ([9c680df](https://github.com/angelacorte/smart-charging-station/commit/9c680df3e21d0231abc3842a85a2f350aeeed553))
* remove useless event ([f4e74d2](https://github.com/angelacorte/smart-charging-station/commit/f4e74d2513ae34809a58a24cbe9d569909cf33b9))
* remove useless service key ([cf326bb](https://github.com/angelacorte/smart-charging-station/commit/cf326bbfda06323eb0976cf6a4b7e6ccd2a65a71))
* **test:** rename test class ([6f4cf3e](https://github.com/angelacorte/smart-charging-station/commit/6f4cf3ebff492ca30ad14ee100b4cf21b678f3c6))
* try to fix access denied ffrom post request ([99655bc](https://github.com/angelacorte/smart-charging-station/commit/99655bc94dbd90776eb624d1498216bfbc6f9cd8))
* update .gitignore ([2e781ff](https://github.com/angelacorte/smart-charging-station/commit/2e781ff427ae0a98ff658440a2eaddc25e16f277))
* update packages structure ([107959b](https://github.com/angelacorte/smart-charging-station/commit/107959bfa9afbd77f2c2ee3cfe3a321a3205101f))


### Style improvements

* add ending comma ([2350885](https://github.com/angelacorte/smart-charging-station/commit/23508850e008c8f8abf303dca43b7f7efcadb357))


### Refactoring

* add replyTo to event ([57a63b6](https://github.com/angelacorte/smart-charging-station/commit/57a63b65b8df639a6bf8317b1ca554ca4e3378a4))
* add reservation as parameter for behavior function ([2d1d816](https://github.com/angelacorte/smart-charging-station/commit/2d1d816facf9452f5f7af2d53b51541901926f38))
* change car implementation ([a1661d8](https://github.com/angelacorte/smart-charging-station/commit/a1661d8fe5860c8e69cedcff48649c71768e8c17))
* change status into state ([b3f4d2a](https://github.com/angelacorte/smart-charging-station/commit/b3f4d2aa8e5567b05e35dce9d0bda22331c6ab1a))
* make cors handler a trait and hand it to the options directive ([4bdfd5e](https://github.com/angelacorte/smart-charging-station/commit/4bdfd5e3c4a5d5a88bab1e8a9048ffea2ec5c118))
* make provider ref an argument of service ([a03811d](https://github.com/angelacorte/smart-charging-station/commit/a03811d8d1180ebc2f45c6cad7405516a7c3d47f))
* make service a child of provider ([9f15fcb](https://github.com/angelacorte/smart-charging-station/commit/9f15fcb2b8f9b4f9ae78e1807ad4b22e76aa9252))
* make the state a parameter of ChargingStation ([dd64976](https://github.com/angelacorte/smart-charging-station/commit/dd64976b1edbb5d765c9148cff5b76a64e1ff06a))
* modify project structure ([909e966](https://github.com/angelacorte/smart-charging-station/commit/909e9669d9456bad1839d1fc57cf8a7c75d6eee7))
* modify project structure ([518f76a](https://github.com/angelacorte/smart-charging-station/commit/518f76acaa6ce52e8ec8611d92df8e3c20c12606))
* move all formats into service package ([95769a6](https://github.com/angelacorte/smart-charging-station/commit/95769a67d0ea07143422b2d353f3ebe24827d2bc))
* move charging station outside of object ([5f1340f](https://github.com/angelacorte/smart-charging-station/commit/5f1340fc474103b1fa53ac989ea16209213a99de))
* move requests result in the corresponding model file ([ef98534](https://github.com/angelacorte/smart-charging-station/commit/ef985340c3c7c63ef6a1d70c375e2742c6d7b524))
* put reservation inside its package ([70cec49](https://github.com/angelacorte/smart-charging-station/commit/70cec49b4680ec61fb40a9288d62588bae265242))
* refactor car ([7b5010f](https://github.com/angelacorte/smart-charging-station/commit/7b5010f5c5fab0cf7ada6a543ff01fe0f87c2be0))
* refactor main app ([4e2d639](https://github.com/angelacorte/smart-charging-station/commit/4e2d639787c5599bd0cdba63c4a5f6b1f0841511))
* refactor of trait names and case class signatures ([ee7673a](https://github.com/angelacorte/smart-charging-station/commit/ee7673aeeeee534521e769c39b2fd1a75d765c51))
* remove duplicate event ([dac7c52](https://github.com/angelacorte/smart-charging-station/commit/dac7c52d06be990fd7070b47963c51d50bd9cb98))
* remove tick event and use stopCharge instead ([b8e44dd](https://github.com/angelacorte/smart-charging-station/commit/b8e44dd1429541ba07a170f243b4b65c7a22b6fe))
* removed status and position from charging station ([99a0124](https://github.com/angelacorte/smart-charging-station/commit/99a0124f8e18e392bfadda7456ca600d47dc3105))
* rename car actor ([8c2a68a](https://github.com/angelacorte/smart-charging-station/commit/8c2a68a0834fdf73a5240d1cb1562850072b8e20))
* rename case class and remove others ([646f21b](https://github.com/angelacorte/smart-charging-station/commit/646f21bc6786f679899b3e4f453621b8ff6b6cb9))
* rewrite main ([f6d24b0](https://github.com/angelacorte/smart-charging-station/commit/f6d24b0f0ecfbda153354c55dafacee95e196ba8))
* **test:** move unit tests in unit package ([0d0bdcb](https://github.com/angelacorte/smart-charging-station/commit/0d0bdcb6394e46fe21c7d62ffc2d5c4d70fba3b8))
* **test:** refactor class name ([a2f4cba](https://github.com/angelacorte/smart-charging-station/commit/a2f4cbacc2abe1ef39bcc6426f6b71be154d542e))
* **test:** refactor test structure ([aee22ed](https://github.com/angelacorte/smart-charging-station/commit/aee22ed4d5ebf4a666a30063a66c88afab9d8a15))
* update car actor behavior ([4efa6c5](https://github.com/angelacorte/smart-charging-station/commit/4efa6c596279dab5667105a30f62290dd4f1f672))

## 1.0.0 (2023-09-01)


### Features

* add a message type for reservation result ([503f501](https://github.com/angelacorte/smart-charging-station/commit/503f501e3820e405af9c678ae130df01a3b0a714))
* add a provider actor for all charging stations ([49724fc](https://github.com/angelacorte/smart-charging-station/commit/49724fc6f76720ec7b0e6ebfb6a4a37387263105))
* add application config files ([8adc3ba](https://github.com/angelacorte/smart-charging-station/commit/8adc3bafaa4cde967aa7138c6fe394f55f7262e6))
* add battery status ([004cee1](https://github.com/angelacorte/smart-charging-station/commit/004cee16b640606e63d27f9740b150356ac4a0ee))
* add car model ([2ac6d46](https://github.com/angelacorte/smart-charging-station/commit/2ac6d4666595f7180630aecf28d6ec31ad4de9ba))
* add charging station actor ([32f05b5](https://github.com/angelacorte/smart-charging-station/commit/32f05b550c6ea0401deb30f1695826363a72f387))
* add charging station status enum and case class ([c824991](https://github.com/angelacorte/smart-charging-station/commit/c824991d1e9614cae4c1d55e82b7dec6cb8b948f))
* add ChargingStationActor class ([14f39d8](https://github.com/angelacorte/smart-charging-station/commit/14f39d846b9c5c195fd7df9f0d5ef6d69e9053c5))
* add cors handler ([4a63add](https://github.com/angelacorte/smart-charging-station/commit/4a63add5d9cade3d9e8403591ca1ac5f32c370f8))
* add cs status to logger ([00ca671](https://github.com/angelacorte/smart-charging-station/commit/00ca67133c46b439ce45acd37d9f163bf370bd25))
* add event for battery actor ([1237b69](https://github.com/angelacorte/smart-charging-station/commit/1237b69c09da2fff375d3a7a6737d7218676c609))
* add first route ([99da0b3](https://github.com/angelacorte/smart-charging-station/commit/99da0b38e096b794edbc4abdf094a557e12f69fa))
* add givens for marshalling and unmarshalling ([95eb0a4](https://github.com/angelacorte/smart-charging-station/commit/95eb0a497e610da1b7d9052812b3aa71f6968287))
* add more charging stations to the sim ([6bb4c4a](https://github.com/angelacorte/smart-charging-station/commit/6bb4c4ab5fd76ab3aef7f6d311b5f12808070394))
* add more fields to the charging station ([629e377](https://github.com/angelacorte/smart-charging-station/commit/629e3773e918efad1c9272a1c246093ca7d8a068))
* add project structure ([54327f7](https://github.com/angelacorte/smart-charging-station/commit/54327f7d1cf795d93a9b433544851ce16c147807))
* add reaction to GetChargingStations ([fc6c746](https://github.com/angelacorte/smart-charging-station/commit/fc6c746d23dd52110a606235d30f2d251aeccdbc))
* add request to start car ([ce23685](https://github.com/angelacorte/smart-charging-station/commit/ce2368521ee35d6a16203dea8117abea5434edbb))
* add reservation model entity ([9dc65b9](https://github.com/angelacorte/smart-charging-station/commit/9dc65b942e01f34dcacd2f4d7ab80059097b6197))
* add route for single charging station ([c9e7a5a](https://github.com/angelacorte/smart-charging-station/commit/c9e7a5af7f08a843420e03ce32e4b73abb4066a5))
* add status parameter in CSUpdated ([2cc1fc5](https://github.com/angelacorte/smart-charging-station/commit/2cc1fc5ff4317e7948789ce7f57a5c8083040dca))
* add status parameter in CSUpdated ([865cb21](https://github.com/angelacorte/smart-charging-station/commit/865cb2148af4acca1daa7bfddc74b0650c969fae))
* add trait for serialization ([69aaef1](https://github.com/angelacorte/smart-charging-station/commit/69aaef1a83250dbc6d7e69c985af479001a54714))
* add user app actor ([4af5a3c](https://github.com/angelacorte/smart-charging-station/commit/4af5a3ca8218183104a01f65ac4fc0704f9e7138))
* add utility extension to ChargingStation ([79c6264](https://github.com/angelacorte/smart-charging-station/commit/79c626411099b613aa25f898613a377040c59669))
* add working main ([832abf1](https://github.com/angelacorte/smart-charging-station/commit/832abf11a32db93d973770461015054614d01cc5))
* complete user app actor ([fca6504](https://github.com/angelacorte/smart-charging-station/commit/fca6504b850a6691ee97b520b97719e9f583faef))
* create an actor representing an http server ([0c87e53](https://github.com/angelacorte/smart-charging-station/commit/0c87e531c963899447f715702303aca97afd9c20))
* create charging station events object ([ea03a73](https://github.com/angelacorte/smart-charging-station/commit/ea03a7330ec19d9225c9803cc6e5f95ff432f44d))
* create user app case class ([fa6fbd0](https://github.com/angelacorte/smart-charging-station/commit/fa6fbd0b7771a6e399a0543a24f67c44d7eb5cbf))
* create user app events ([a135a50](https://github.com/angelacorte/smart-charging-station/commit/a135a50eea3d2eb3c92e6c6915c7680d1da496d0))
* implement ask state and change state messages ([b6a2842](https://github.com/angelacorte/smart-charging-station/commit/b6a2842990cda529feeb48c1aa40546542179925))
* implement car actor ([4edb00f](https://github.com/angelacorte/smart-charging-station/commit/4edb00f97260ae73312bdfbe08389bc7c5d471e4))
* implement charge request ([57c3020](https://github.com/angelacorte/smart-charging-station/commit/57c3020b3950fb55f1d1f3accc2c19665d5cee29))
* implement charging station actor ([fc2667b](https://github.com/angelacorte/smart-charging-station/commit/fc2667bdc40c0ad0ec7d20dd46a524b9e7f686b2))
* implement reservation api route ([d9e603f](https://github.com/angelacorte/smart-charging-station/commit/d9e603fec2a4723af1446be386d8baf61dad2bc3))
* implement reserve request ([8c30e57](https://github.com/angelacorte/smart-charging-station/commit/8c30e575dbef839d763464b9216840be0d2350fc))
* introduce battery as a concept and actor ([ef7c6fc](https://github.com/angelacorte/smart-charging-station/commit/ef7c6fc9e08b4a1d38e7bb65fa99c6249f269ad0))
* make charging stations listen for providers and vice versa ([fe9ee26](https://github.com/angelacorte/smart-charging-station/commit/fe9ee26aae406104e38666d0a8f28f52a19a8dfd))
* partially implement charging station actor ([80c7b5d](https://github.com/angelacorte/smart-charging-station/commit/80c7b5dab16461d43f287c8584238916aa6ea7db))


### Dependency updates

* **deps:** add akka dependency ([ad00bed](https://github.com/angelacorte/smart-charging-station/commit/ad00bed1ac4c778a59c6bf4807d1a89f45024d09))
* **deps:** add Akka HTTP and Akka Streams ([4b313a6](https://github.com/angelacorte/smart-charging-station/commit/4b313a64cd8b27791afaf4d34fa1c160a62f5f5c))
* **deps:** add dependency ([95a5033](https://github.com/angelacorte/smart-charging-station/commit/95a5033097f0c7153b68e18728ed8efefd24a209))
* **deps:** update dependencies ([785dd1a](https://github.com/angelacorte/smart-charging-station/commit/785dd1a05e3edd71b244a8ccc459afe31592d607))


### Bug Fixes

* add import ([85a241d](https://github.com/angelacorte/smart-charging-station/commit/85a241d0d11932dc289261fe52a624abcf85c5d7))
* add missing import ([f9d1650](https://github.com/angelacorte/smart-charging-station/commit/f9d1650c8a058105deb5ffa603716054396d8ad3))
* **ci:** add release job check to success ([7664c92](https://github.com/angelacorte/smart-charging-station/commit/7664c92267ff17833e5f81d5294e7c3fae74a68c))
* **deps:** add necessary dependencies ([ec9f9f7](https://github.com/angelacorte/smart-charging-station/commit/ec9f9f7fba6d2a822c7fa4bf24c08301b6cd4b1e))
* **deps:** fix broken version reference ([19b0437](https://github.com/angelacorte/smart-charging-station/commit/19b04377d532fb2ea1c42119f875227eb5a5b628))
* fix bug due to multiple service spawning by separating setup and running logic ([d6058b3](https://github.com/angelacorte/smart-charging-station/commit/d6058b33e7a8e7023d8e5c9677481097075ce15a))
* fix bug due to wrong type expected ([98592a6](https://github.com/angelacorte/smart-charging-station/commit/98592a69d4a4638d053758a5a8dcc0f426925f53))
* fix case class extension ([0f69a42](https://github.com/angelacorte/smart-charging-station/commit/0f69a4231a70e6f7ed68f46f3961f28299f1263b))
* fix imports ([9ee9f5f](https://github.com/angelacorte/smart-charging-station/commit/9ee9f5f67bfd38d61f829c1983c894f6ddb34161))
* fix messageAdapter call ([4fdf1aa](https://github.com/angelacorte/smart-charging-station/commit/4fdf1aac53deee100cdfbb536410270112a14ccb))
* fix problems and refactor signatures ([1609774](https://github.com/angelacorte/smart-charging-station/commit/160977427979032a6e1fcb27313583644c76095d))
* fix workflow ([96e1d0c](https://github.com/angelacorte/smart-charging-station/commit/96e1d0c8502f6b0529f5e7de2fe959dd73a6fb6e))
* make ChargingStation serializable with cbor ([f03c74f](https://github.com/angelacorte/smart-charging-station/commit/f03c74fb3373d709c51a1dddedf714dd19dc862a))
* make events serializable with cbor ([b082a03](https://github.com/angelacorte/smart-charging-station/commit/b082a03fce34ff9041013402848f1af7106fe4b4))
* make provider register to receptionist ([31feb97](https://github.com/angelacorte/smart-charging-station/commit/31feb976ad6c4fb7d3c76c658361ee92f95fd0f4))
* make server respond with appropriate status code for failures ([da28f22](https://github.com/angelacorte/smart-charging-station/commit/da28f221149b1da11cca653ab374110f8b98ce93))
* notify providers when updating ([d4ed570](https://github.com/angelacorte/smart-charging-station/commit/d4ed570c1551d3bd9a9705c51331272b111753b7))
* remove unused import ([eb64c19](https://github.com/angelacorte/smart-charging-station/commit/eb64c199a78d0fda2127062e96089db39b2abcae))
* remove useless argument in GetChargingStations ([c7f764c](https://github.com/angelacorte/smart-charging-station/commit/c7f764cc41093ea54fd96f466b5422417fd437fd))
* return explicit behavior ([fd207ac](https://github.com/angelacorte/smart-charging-station/commit/fd207acad7f4c9714496564ad19fe13337791622))
* **test:** create the charging station correctly ([0289a00](https://github.com/angelacorte/smart-charging-station/commit/0289a009d8a5cd406d01a281ba0ede356dcdeaf5))
* **test:** fix creation test ([ce98195](https://github.com/angelacorte/smart-charging-station/commit/ce98195818cf844126bf50201f0ffee97ba207f3))
* **test:** fixed and refactored unit tests ([6c02d5e](https://github.com/angelacorte/smart-charging-station/commit/6c02d5e8cd3b623438bcf9ebfb7225190c4b927a))
* **test:** remove useless test ([15c940a](https://github.com/angelacorte/smart-charging-station/commit/15c940a108a12fc2678e4c3e5b2c505933b424c8))
* update case class signature ([a50eab7](https://github.com/angelacorte/smart-charging-station/commit/a50eab7111bcf73dc144e66680a09df9b6627902))
* update the providers set upon receiving receptionist event ([5d2e29b](https://github.com/angelacorte/smart-charging-station/commit/5d2e29b8096b0105a36eafa9d4c583fe99ca0499))


### Tests

* add car actor test ([6d02b2a](https://github.com/angelacorte/smart-charging-station/commit/6d02b2ade04431812584997630be1585f3a8edd2))
* add test base class ([555930b](https://github.com/angelacorte/smart-charging-station/commit/555930be66f71ee84d31414be71d1fcb6b61ab94))
* add test for charging response and refactoring ([867e42b](https://github.com/angelacorte/smart-charging-station/commit/867e42b8b3711a2debf29d26ee6656031cc573e0))
* add test for charging response Ok ([36e52c1](https://github.com/angelacorte/smart-charging-station/commit/36e52c136b639918e66fe517ffc8b0ca2fa63ab2))
* add test for cs send state ([f508543](https://github.com/angelacorte/smart-charging-station/commit/f508543e16f519a70b13d3a758022e2be94d484b))
* add test for cs update state ([5bd0f40](https://github.com/angelacorte/smart-charging-station/commit/5bd0f400b0288cfe5d6e724cdb93c93ce3d8b000))
* complete charging station tests ([9a30935](https://github.com/angelacorte/smart-charging-station/commit/9a309355bac199fe9dd5746432c32ae3b2367868))
* create test class for user app ([96019c3](https://github.com/angelacorte/smart-charging-station/commit/96019c3461a298824444bbf3aca826f281e91192))
* fix parameter type in test ([592e479](https://github.com/angelacorte/smart-charging-station/commit/592e479d0b9d07af8c08a4da83c4ae6f9298b38a))
* update test style and refactor test ([345197f](https://github.com/angelacorte/smart-charging-station/commit/345197f7496703cfc80d62c7c93bca116f3bf306))


### Build and continuous integration

* add release job ([c2fee75](https://github.com/angelacorte/smart-charging-station/commit/c2fee759f537367507be3859151c4cb6236f6f74))
* remove unsupported jdks ([b4d69fe](https://github.com/angelacorte/smart-charging-station/commit/b4d69fef3841d7504b02bfa35eed7ae24aa3f66f))
* remove unsupported jdks from ci test run ([b194b28](https://github.com/angelacorte/smart-charging-station/commit/b194b285d33477325f779c1a44ee130e0df14ac4))


### General maintenance

* add .gitattributes ([b182989](https://github.com/angelacorte/smart-charging-station/commit/b182989f88132a8c7e58549cc97e04ef70b81b9c))
* add import ([49b3fab](https://github.com/angelacorte/smart-charging-station/commit/49b3fab7da46764fdb33b1b24115cbc37c68e9fd))
* add semantic release config ([f244dc6](https://github.com/angelacorte/smart-charging-station/commit/f244dc6cc2dd4306fd741af8d9ef40ba338318e9))
* add workflows ([b4f78a5](https://github.com/angelacorte/smart-charging-station/commit/b4f78a5d05bc1989b071efc52c4eccf9b75439fa))
* black magic exists? ([f47e005](https://github.com/angelacorte/smart-charging-station/commit/f47e00524b2bf63f87b02e087eab4a1d9ecfca4a))
* **config:** allow multi mbeans in same jvm ([bd609f6](https://github.com/angelacorte/smart-charging-station/commit/bd609f67ab3291aabcf40c134a206693a2954fa9))
* extend charging time ([b09df2d](https://github.com/angelacorte/smart-charging-station/commit/b09df2dab975fe75ca7e287f7d0486c7ada567c4))
* go back to old version ([386afe1](https://github.com/angelacorte/smart-charging-station/commit/386afe1a5bdd9f8fc83c9499647955b43938d3f6))
* optimize import ([f0dcfce](https://github.com/angelacorte/smart-charging-station/commit/f0dcfce8ee3f01931b7477a0987aaa8b28e767be))
* optimize imports ([7149641](https://github.com/angelacorte/smart-charging-station/commit/7149641238341235ae62a06c05af6c584102bf43))
* optimize imports ([398f8c1](https://github.com/angelacorte/smart-charging-station/commit/398f8c1715705a274960c94b8a137125a599518e))
* optimize imports and refactor cors application ([c999e58](https://github.com/angelacorte/smart-charging-station/commit/c999e5819a928726d21ca972eb91e140a0172f0f))
* remove comment ([e20a1a4](https://github.com/angelacorte/smart-charging-station/commit/e20a1a46413b0956970639eaee37447dc83be5ed))
* remove unused class ([89f3b4f](https://github.com/angelacorte/smart-charging-station/commit/89f3b4f38f49ba20a7e2d829c9ac30e26ebdadca))
* remove unused messages ([3bb544f](https://github.com/angelacorte/smart-charging-station/commit/3bb544fa7ecce99875618dd6a58367616e4c530a))
* remove useless check ([db045fe](https://github.com/angelacorte/smart-charging-station/commit/db045fed11b1154275423ce4b9f2190044d87dab))
* remove useless concepts ([169ada9](https://github.com/angelacorte/smart-charging-station/commit/169ada90353bcbd70d512b0ea04d4e366935dd3c))
* remove useless concepts ([9c680df](https://github.com/angelacorte/smart-charging-station/commit/9c680df3e21d0231abc3842a85a2f350aeeed553))
* remove useless event ([f4e74d2](https://github.com/angelacorte/smart-charging-station/commit/f4e74d2513ae34809a58a24cbe9d569909cf33b9))
* remove useless service key ([cf326bb](https://github.com/angelacorte/smart-charging-station/commit/cf326bbfda06323eb0976cf6a4b7e6ccd2a65a71))
* **test:** rename test class ([6f4cf3e](https://github.com/angelacorte/smart-charging-station/commit/6f4cf3ebff492ca30ad14ee100b4cf21b678f3c6))
* try to fix access denied ffrom post request ([99655bc](https://github.com/angelacorte/smart-charging-station/commit/99655bc94dbd90776eb624d1498216bfbc6f9cd8))
* update .gitignore ([2e781ff](https://github.com/angelacorte/smart-charging-station/commit/2e781ff427ae0a98ff658440a2eaddc25e16f277))
* update packages structure ([107959b](https://github.com/angelacorte/smart-charging-station/commit/107959bfa9afbd77f2c2ee3cfe3a321a3205101f))


### Style improvements

* add ending comma ([2350885](https://github.com/angelacorte/smart-charging-station/commit/23508850e008c8f8abf303dca43b7f7efcadb357))


### Refactoring

* add replyTo to event ([57a63b6](https://github.com/angelacorte/smart-charging-station/commit/57a63b65b8df639a6bf8317b1ca554ca4e3378a4))
* add reservation as parameter for behavior function ([2d1d816](https://github.com/angelacorte/smart-charging-station/commit/2d1d816facf9452f5f7af2d53b51541901926f38))
* change car implementation ([a1661d8](https://github.com/angelacorte/smart-charging-station/commit/a1661d8fe5860c8e69cedcff48649c71768e8c17))
* change status into state ([b3f4d2a](https://github.com/angelacorte/smart-charging-station/commit/b3f4d2aa8e5567b05e35dce9d0bda22331c6ab1a))
* make cors handler a trait and hand it to the options directive ([4bdfd5e](https://github.com/angelacorte/smart-charging-station/commit/4bdfd5e3c4a5d5a88bab1e8a9048ffea2ec5c118))
* make provider ref an argument of service ([a03811d](https://github.com/angelacorte/smart-charging-station/commit/a03811d8d1180ebc2f45c6cad7405516a7c3d47f))
* make service a child of provider ([9f15fcb](https://github.com/angelacorte/smart-charging-station/commit/9f15fcb2b8f9b4f9ae78e1807ad4b22e76aa9252))
* make the state a parameter of ChargingStation ([dd64976](https://github.com/angelacorte/smart-charging-station/commit/dd64976b1edbb5d765c9148cff5b76a64e1ff06a))
* modify project structure ([909e966](https://github.com/angelacorte/smart-charging-station/commit/909e9669d9456bad1839d1fc57cf8a7c75d6eee7))
* modify project structure ([518f76a](https://github.com/angelacorte/smart-charging-station/commit/518f76acaa6ce52e8ec8611d92df8e3c20c12606))
* move charging station outside of object ([5f1340f](https://github.com/angelacorte/smart-charging-station/commit/5f1340fc474103b1fa53ac989ea16209213a99de))
* put reservation inside its package ([70cec49](https://github.com/angelacorte/smart-charging-station/commit/70cec49b4680ec61fb40a9288d62588bae265242))
* refactor car ([7b5010f](https://github.com/angelacorte/smart-charging-station/commit/7b5010f5c5fab0cf7ada6a543ff01fe0f87c2be0))
* refactor main app ([4e2d639](https://github.com/angelacorte/smart-charging-station/commit/4e2d639787c5599bd0cdba63c4a5f6b1f0841511))
* refactor of trait names and case class signatures ([ee7673a](https://github.com/angelacorte/smart-charging-station/commit/ee7673aeeeee534521e769c39b2fd1a75d765c51))
* remove tick event and use stopCharge instead ([b8e44dd](https://github.com/angelacorte/smart-charging-station/commit/b8e44dd1429541ba07a170f243b4b65c7a22b6fe))
* removed status and position from charging station ([99a0124](https://github.com/angelacorte/smart-charging-station/commit/99a0124f8e18e392bfadda7456ca600d47dc3105))
* rename car actor ([8c2a68a](https://github.com/angelacorte/smart-charging-station/commit/8c2a68a0834fdf73a5240d1cb1562850072b8e20))
* rename case class and remove others ([646f21b](https://github.com/angelacorte/smart-charging-station/commit/646f21bc6786f679899b3e4f453621b8ff6b6cb9))
* rewrite main ([f6d24b0](https://github.com/angelacorte/smart-charging-station/commit/f6d24b0f0ecfbda153354c55dafacee95e196ba8))
* **test:** move unit tests in unit package ([0d0bdcb](https://github.com/angelacorte/smart-charging-station/commit/0d0bdcb6394e46fe21c7d62ffc2d5c4d70fba3b8))
* **test:** refactor class name ([a2f4cba](https://github.com/angelacorte/smart-charging-station/commit/a2f4cbacc2abe1ef39bcc6426f6b71be154d542e))
* **test:** refactor test structure ([aee22ed](https://github.com/angelacorte/smart-charging-station/commit/aee22ed4d5ebf4a666a30063a66c88afab9d8a15))
* update car actor behavior ([4efa6c5](https://github.com/angelacorte/smart-charging-station/commit/4efa6c596279dab5667105a30f62290dd4f1f672))
