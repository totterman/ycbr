DROP TABLE IF EXISTS boats;
DROP INDEX IF EXISTS idx_boats_created_at;
DROP INDEX IF EXISTS idx_boats_make;
DROP INDEX IF EXISTS idx_boats_name;
DROP INDEX IF EXISTS idx_boats_owner;

DROP TABLE IF EXISTS i9events;
DROP INDEX IF EXISTS idx_i9events_place;
DROP INDEX IF EXISTS idx_i9events_starts;

DROP TABLE IF EXISTS boat_bookings;
DROP TABLE IF EXISTS inspector_registrations;

DROP TABLE IF EXISTS inspections;
DROP INDEX IF EXISTS idx_inspections_boat_id;
DROP INDEX IF EXISTS idx_inspections_inspector_name;

DROP TABLE IF EXISTS inspection_data;
DROP TABLE IF EXISTS hull_data;
DROP TABLE IF EXISTS rig_data;
DROP TABLE IF EXISTS engine_data;
DROP TABLE IF EXISTS equipment_data;
DROP TABLE IF EXISTS navigation_data;
DROP TABLE IF EXISTS safety_data;

CREATE TABLE IF NOT EXISTS boats (
	boat_id UUID DEFAULT uuidv7() UNIQUE NOT NULL,
	"owner" varchar(50) NOT NULL,
	"name" varchar(50) NOT NULL,
	kind varchar(1) NOT NULL,
	sign varchar(50) NULL,
	make varchar(50) NULL,
	model varchar(50) NULL,
	loa float8 NULL,
	draft float8 NULL,
	beam float8 NULL,
	deplacement float8 NULL,
	engines varchar(50) NULL,
	"year" varchar(4) NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(50) NOT NULL,
	modified_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	modified_by varchar(50) NULL,
	"version" int4 NOT NULL,
	CONSTRAINT boats_pkey PRIMARY KEY (boat_id)
);
CREATE INDEX IF NOT EXISTS idx_boats_created_at ON boats USING btree (created_at);
CREATE INDEX IF NOT EXISTS idx_boats_boat_id ON boats USING btree (boat_id);
CREATE INDEX IF NOT EXISTS idx_boats_make ON boats USING btree (make);
CREATE INDEX IF NOT EXISTS idx_boats_name ON boats USING btree (name);
CREATE INDEX IF NOT EXISTS idx_boats_owner ON boats USING btree (owner);

CREATE TABLE IF NOT EXISTS i9events (
	i9event_id UUID DEFAULT uuidv7() UNIQUE NOT NULL,
	place varchar(50) NOT NULL,
	starts timestamptz NOT NULL,
	ends timestamptz NOT NULL,
	created_at timestamptz NULL,
	created_by varchar(50) NULL,
	modified_at timestamptz NULL,
	modified_by varchar(50) NULL,
	"version" int4 NOT NULL,
	CONSTRAINT i9events_pkey PRIMARY KEY (i9event_id)
);
CREATE INDEX IF NOT EXISTS idx_i9events_place ON i9events USING btree (place);
CREATE INDEX IF NOT EXISTS idx_i9events_starts ON i9events USING btree (starts);

CREATE TABLE IF NOT EXISTS inspector_registrations (
	i9events UUID NULL,
	inspector_name varchar(50) NOT NULL,
	message varchar(50) NULL,
	created_at timestamptz NULL,
	created_by varchar(50) NULL
);

CREATE TABLE IF NOT EXISTS boat_bookings (
	i9events UUID NULL,
	boat_id UUID NOT NULL,
	message varchar(50) NULL,
	taken bool DEFAULT false NULL,
	created_at timestamptz NULL,
	created_by varchar(50) NULL
);

CREATE TABLE IF NOT EXISTS inspections (
	inspection_id uuid DEFAULT uuidv7() NOT NULL,
	"timestamp" timestamptz(6) NULL,
	inspector_name varchar(50) NOT NULL,
	boat_id uuid NOT NULL,
	event_id uuid NOT NULL,
	inspection_class varchar(1) NOT NULL,
	completed timestamptz(6) NULL,
	created_at timestamptz(6) NULL,
	created_by varchar(50) NULL,
	modified_at timestamptz(6) NULL,
	modified_by varchar(50) NULL,
	"version" int4 NOT NULL,
	CONSTRAINT inspections_pkey PRIMARY KEY (inspection_id)
);
CREATE INDEX IF NOT EXISTS idx_inspections_boat_id ON inspections USING btree (boat_id);
CREATE INDEX IF NOT EXISTS idx_inspections_inspector_name ON inspections USING btree (inspector_name);

CREATE TABLE IF NOT EXISTS inspection_data (
	inspections UUID NOT NULL
);

CREATE TABLE IF NOT EXISTS hull_data (
	inspections UUID NOT NULL,
	hull bool NULL,
	openings bool NULL,
	material bool NULL,
	keel_rudder bool null,
	steering bool NULL,
	drive_shaft_prop bool NULL,
	throughulls bool NULL,
	fall_protection bool NULL,
  	heavy_objects bool NULL,
  	fresh_water bool NULL,
  	lowest_leak int NULL
);

CREATE TABLE IF NOT EXISTS rig_data (
	inspections UUID NOT NULL,
	rig bool NULL,
	sails bool NULL,
	storm_sails bool NULL,
	reefing bool NULL
);

CREATE TABLE IF NOT EXISTS engine_data (
	inspections UUID NOT NULL,
	installation bool NULL,
	controls bool NULL,
	fuel_system bool NULL,
  	cooling bool NULL,
  	strainer bool NULL,
  	separate_batteries bool NULL,
  	shore_power bool NULL,
  	"aggregate" bool NULL,
  	reserve bool NULL
);

CREATE TABLE IF NOT EXISTS equipment_data (
	inspections UUID NOT NULL,
	markings bool NULL,
  	anchors bool NULL,
  	sea_anchor bool NULL,
  	lines bool NULL,
  	tools bool NULL,
  	paddel bool NULL,
  	hook bool NULL,
  	resque_line bool NULL,
  	fenders bool NULL,
  	ladders bool NULL,
  	defroster bool NULL,
  	toilet bool NULL,
  	gas_system bool NULL,
  	stove bool NULL,
  	flag bool null
);

CREATE TABLE IF NOT EXISTS navigation_data (
	inspections UUID NOT NULL,
	lights bool NULL,
  	dayshapes bool NULL,
  	horn bool NULL,
  	reflector bool NULL,
  	compass bool NULL,
  	bearing bool NULL,
  	log bool NULL,
  	charts bool NULL,
  	radio bool NULL,
  	satnav bool NULL,
  	radar bool NULL,
  	spotlight bool NULL,
  	vhf bool NULL,
  	hand_vhf bool NULL,
  	documents bool NULL
);

CREATE TABLE IF NOT EXISTS safety_data (
	inspections UUID NOT NULL,
	buoyancy bool NULL,
  	harness bool NULL,
  	lifebuoy bool NULL,
  	signals_a bool NULL,
  	signals_b bool NULL,
  	fixed_handpump bool NULL,
  	electric_pump bool NULL,
  	hand_extinguisher bool NULL,
  	fire_blanket bool NULL,
  	plugs bool NULL,
  	flashlight bool NULL,
  	firstaid bool NULL,
  	spare_steering bool NULL,
  	emergency_tools bool NULL,
  	reserves bool NULL,
  	liferaft bool NULL,
  	detector bool NULL
);

