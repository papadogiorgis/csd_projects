/*PAPADAKIS GEORGIOS 4975
 *ASSIGNMENT 3
 *SYMTABLE.H           */

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <string.h>

typedef struct SymTable_S *SymTable_T;

SymTable_T SymTable_new(void);

void SymTable_free(SymTable_T oSymTable);

unsigned int SymTable_getLength(SymTable_T oSymTable);

int SymTable_put(SymTable_T oSymTable, const char *pcKey, const void *pvValue);

int SymTable_remove(SymTable_T oSymTable, const char *pcKey);

int SymTable_contains(SymTable_T oSymTable, const char *pcKey);

void *SymTable_get(SymTable_T oSymTable, const char *pcKey);

void SymTable_map(
    SymTable_T oSymTable,
    void (*pfApply)(const char *pcKey, void *pvValue, void *pvExtra),
    const void *pvExtra
);
