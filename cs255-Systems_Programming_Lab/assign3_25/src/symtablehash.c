/*PAPADAKIS GEORGIOS 4975
 *ASSIGNMENT 3
 *SYMTABLEHASH.C       */

#include "symtable.h"

#define HASH_MULTIPLIER 65599

/* bucket sizes for resizing */
static const unsigned int bucket_size[] = {
    509, 1021, 2053, 4093, 8191, 16381, 32771, 65521
};

/* bindings' struct */
struct binding{
    char * key;
    void * value;
    struct binding * next;
};

/* The hashtable.
   Each bucket is a single linked list. */
struct SymTable_S{
    struct binding ** table;
    unsigned int buckets;
    unsigned int length;
    unsigned int bucket_size_index;
};

/* Return a hash code for pcKey. */
static unsigned int SymTable_hash(const char *pcKey, unsigned int buckets){
    size_t ui;
    unsigned int uiHash = 0U;
    for (ui = 0U; pcKey[ui] != '\0'; ui++){
        uiHash = uiHash * HASH_MULTIPLIER + pcKey[ui];
    }
    return uiHash % buckets;
}

static void rehash(SymTable_T oSymTable){
    unsigned int nsize = bucket_size[oSymTable->bucket_size_index + 1];
    unsigned int i = 0U;
    unsigned int nposition;
    struct binding * current;
    struct binding * next;

    struct binding ** ntable = calloc(nsize, sizeof(struct binding *));
    if(ntable == NULL){
        fprintf(stderr, "Error: calloc() failed\n");
        exit(EXIT_FAILURE);
    }

    while(i < oSymTable->buckets){
        current = oSymTable->table[i];
        while(current != NULL){
            next = current->next;

            nposition = SymTable_hash(current->key, nsize);

            current->next = ntable[nposition];
            ntable[nposition] = current;

            current = next;
        }
        i++;
    }

    free(oSymTable->table);
    oSymTable->table = ntable;
    oSymTable->buckets = nsize;
    oSymTable->bucket_size_index++;
}

/* makes and returns a new empty SymTable_T */
SymTable_T SymTable_new(void){
    int size = sizeof(struct SymTable_S);
    SymTable_T temp = malloc(size);
    if(temp == NULL){
        fprintf(stderr, "Error: malloc() failed\n");
        exit(EXIT_FAILURE);
    }

    temp->table = calloc(bucket_size[0],  sizeof(struct binding*));
    if(temp->table == NULL){
        free(temp);
        fprintf(stderr, "Error: calloc() failed\n");
        exit(EXIT_FAILURE);
    }

    temp->length = 0;
    temp->bucket_size_index = 0;
    temp->buckets = bucket_size[0];
    return temp;
}

/* deallocates the hashtable oSymTable */
void SymTable_free(SymTable_T oSymTable){
    struct binding * temp;
    struct binding * ntemp;
    unsigned int i = 0U;

    if(oSymTable == NULL){
        return;
    }
    while(i < oSymTable->buckets){
        temp = oSymTable->table[i];
        while(temp != NULL){
            ntemp = temp->next;
            free(temp->key);
            free(temp);
            temp = ntemp;
        }
        i++;
    }
    free(oSymTable->table);
    free(oSymTable);
}

/* returns the amount of bindings */
unsigned int SymTable_getLength(SymTable_T oSymTable){
    unsigned int l;
    
    assert(oSymTable != NULL);

    l = oSymTable->length;
    return l;
}

/* saves a new binding */
int SymTable_put(SymTable_T oSymTable, const char *pcKey, const void *pvValue){
    int position;
    struct binding *new_binding;
    struct binding *temp;

    assert(oSymTable != NULL);
    assert(pcKey != NULL);
    assert(pvValue != NULL);

    if(SymTable_contains(oSymTable, pcKey) == 1){
        return 0;
    }
    
    if((oSymTable->length >= bucket_size[oSymTable->bucket_size_index])&&
(oSymTable->bucket_size_index + 1 < (sizeof(bucket_size)/sizeof(bucket_size[0])))){
        rehash(oSymTable);
    }

    new_binding = (struct binding*) malloc(sizeof(struct binding));
    if(new_binding == NULL){
        fprintf(stderr, "Error: malloc() failed\n");
        exit(EXIT_FAILURE);
    }
    new_binding->key = (char *) malloc(1 + strlen(pcKey));
    if(new_binding->key == NULL){
        free(new_binding);
        fprintf(stderr, "Error: malloc() failed\n");
        exit(EXIT_FAILURE);
    }

    strcpy(new_binding->key, pcKey);
    new_binding->value = (void *) pvValue;
    new_binding->next = NULL;
    
    position = SymTable_hash(pcKey, oSymTable->buckets);
    temp = oSymTable->table[position];
    if(oSymTable->table[position] == NULL){
        oSymTable->table[position] = new_binding;
    }else{
        while(temp->next != NULL){
            temp = temp->next;
        }
        temp->next = new_binding;
    }
    oSymTable->length++;

    return 1;
}

/* removes a binding */
int SymTable_remove(SymTable_T oSymTable, const char *pcKey){
    struct binding *current;
    struct binding *previous;
    int position;

    assert(oSymTable != NULL);
    assert(pcKey != NULL);

    position = SymTable_hash(pcKey, oSymTable->buckets);
    current = oSymTable->table[position];
    previous = NULL;
    while(current != NULL){
        if(strcmp(pcKey, current->key) == 0){
            if(previous == NULL){
                oSymTable->table[position] = current->next;
            }else{
                previous->next = current->next;
            }
            free(current->key);
            free(current);
            oSymTable->length--;
            return 1;
        }

        previous = current;
        current = current->next;
    }

    return 0;
}

/* checks if a key is in the list */
int SymTable_contains(SymTable_T oSymTable, const char *pcKey){
    struct binding * current;
    int position;
    assert(oSymTable != NULL);
    assert(pcKey != NULL);

    position = SymTable_hash(pcKey, oSymTable->buckets);
    current = oSymTable->table[position];
    while(current != NULL){
        if(strcmp(pcKey, current->key) == 0){
            return 1;
        }
        current = current->next;
    }
    return 0;
}

/* returns the binding with the key pckey */
void *SymTable_get(SymTable_T oSymTable, const char *pcKey){
    struct binding * current;
    int position;
    assert(oSymTable != NULL);
    assert(pcKey != NULL);

    position = SymTable_hash(pcKey, oSymTable->buckets);
    current = oSymTable->table[position];
    while(current != NULL){
        if(strcmp(pcKey, current->key) == 0){
            return current->value;
        }
        current = current->next;
    }
    return NULL;
}

/* it maps the bindings */
void SymTable_map(
    SymTable_T oSymTable,
    void (*pfApply)(const char *pcKey, void *pvValue, void *pvExtra),
    const void *pvExtra
){
    struct binding * current;
    int i=0;
    assert(oSymTable != NULL);
    assert(pfApply != NULL);

    while(i < oSymTable->buckets){
        current = oSymTable->table[i];
        while(current != NULL){
            pfApply((const char *)(current->key), current->value, (void *)pvExtra);
            current = current->next;
        }
        i++;
    }
}
