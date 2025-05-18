/*PAPADAKIS GEORGIOS 4975
 *ASSIGNMENT 3
 *SYMTABLELIST.C       */

#include "symtable.h"

/* bindings' struct */
struct binding{
    char * key;
    void * value;
    struct binding *next;
};

/* list struct */
struct SymTable_S{
    struct binding * list_head;
    unsigned int list_length;
};

/* makes and returns a new empty SymTable_T */
SymTable_T SymTable_new(void){
    int size = sizeof(struct SymTable_S);
    SymTable_T temp = malloc(size);
    if(temp == NULL){
        fprintf(stderr, "Error: malloc() failed\n");
        exit(EXIT_FAILURE);
    }
    temp->list_head = NULL;
    temp->list_length = 0;
    return temp;
}

/* deallocates the list oSymTable */
void SymTable_free(SymTable_T oSymTable){
    struct binding *temp;
    struct binding *ntemp;

    if(oSymTable == NULL){
        return;
    }

    temp = oSymTable->list_head;
    while(temp != NULL){
        ntemp = temp->next;
        free(temp->key);
        free(temp);
        temp = ntemp;
    }
    free(oSymTable);
}

/* returns the amount of bindings */
unsigned int SymTable_getLength(SymTable_T oSymTable){
    unsigned int l;

    assert(oSymTable != NULL);
    
    l = oSymTable->list_length;
    return l;
}

/* saves a new binding */
int SymTable_put(SymTable_T oSymTable, const char *pcKey, const void *pvValue){
    int flag = SymTable_contains(oSymTable, pcKey);
    struct binding *new_binding;
    struct binding *temp;

    assert(oSymTable != NULL);
    assert(pcKey != NULL);
    assert(pvValue != NULL);

    if(flag == 1){
        return 0;
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
    oSymTable->list_length++;
    if(oSymTable->list_head == NULL){
        oSymTable->list_head = new_binding;
    }else{
        temp = oSymTable->list_head;
        while(temp->next != NULL){
            temp = temp->next;
        }
        temp->next = new_binding;
    }

    return 1;
}

/* removes a binding */
int SymTable_remove(SymTable_T oSymTable, const char *pcKey){
    struct binding *current;
    struct binding *previous;

    assert(oSymTable != NULL);
    assert(pcKey != NULL);

    current = oSymTable->list_head;
    previous = NULL;
    while(current != NULL){
        if(strcmp(current->key, pcKey) == 0){
            if(previous == NULL){
                oSymTable->list_head = oSymTable->list_head->next;
            }else{
                previous->next = current->next;
            }
            free(current->key);
            free(current);
            oSymTable->list_length--;
            return 1;
        }
        previous = current;
        current = current->next;
    }
    return 0;
}

/* checks if a key is in the list */
int SymTable_contains(SymTable_T oSymTable, const char *pcKey){
    struct binding *current;
    assert(oSymTable != NULL);
    assert(pcKey != NULL);

    current = oSymTable->list_head;
    while(current != NULL){
        if(strcmp(current->key, pcKey) == 0){
            return 1;
        }
        current = current->next;
    }
    return 0;
}

/* returns the binding with the key pckey */
void *SymTable_get(SymTable_T oSymTable, const char *pcKey){
    struct binding *current;
    assert(oSymTable != NULL);
    assert(pcKey != NULL);

    current = oSymTable->list_head;
    while(current != NULL){
        if(strcmp(current->key, pcKey) == 0){
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
    struct binding *current;
    assert(oSymTable != NULL);
    assert(pfApply != NULL);

    current = oSymTable->list_head;
    while(current != NULL){
        pfApply((const char *)(current->key), current->value, (void *)pvExtra);
        current = current->next;
    }
}
