-- for collection --
alter table collection add column doc tsvector;
create index doc_coll_index on collection using gin(doc);
update collection set doc = to_tsvector(collection.name || ' ' || collection.description);


create function collection_tsvektor_trigger() returns trigger as $$
BEGIN
    new.doc:= setweight(to_tsvector(new.name),'B') ||
              setweight(to_tsvector(new.description),'A');
    return new;
END
$$ language plpgsql;

create trigger coll_tsvektor_update before insert or update on collection
    for each row execute procedure collection_tsvektor_trigger();

-- for item --
alter table item add  column  doc tsvector;
create index doc_item_index on item using gin(doc);
update item set doc = to_tsvector(item.name);

create function item_tsvektor_trigger() returns trigger as $$
BEGIN
    new.doc := to_tsvector(new.name);
    return new;
END
$$ language plpgsql;

-- drop function item_tsvektor_trigger();

create trigger item_tsvektor_update before insert or update on item
    for each row execute procedure item_tsvektor_trigger();

-- for comment --
alter table comment add column doc tsvector;
create index doc_comment_index on comment using gin(doc);
update comment set doc = to_tsvector(comment.text);


create function comment_tsvektor_trigger() returns trigger as $$
BEGIN
    new.doc:= to_tsvector(new.text);
    return new;
END
$$ language plpgsql;

create trigger comment_tsvektor_update before insert or update on comment
    for each row execute procedure comment_tsvektor_trigger();
