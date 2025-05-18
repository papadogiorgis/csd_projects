/*PAPADAKIS GEORGIOS 4975
 *ASSIGNMENT 3
 *TESTSYMTAB.C         */

 #include "symtable.h"

int main()
{
    SymTable_T papadolist  = SymTable_new();
    int i;
    char apotelesma[100];

    if(SymTable_put(papadolist,"papadogiorgis","1010")){
        printf("ekanes put\n");
      }else{
        printf("SymTable_put exei thema\n");
      }

    if(!SymTable_put(papadolist,"papadogiorgis","1010")){
        printf("den ekane push deutero, douleuei\n");
      }else{
        printf("SymTable_put exei thema, ekane put 2 fores to idio pragma\n");
      }

    if(SymTable_put(papadolist,"eri","12")){
        printf("ekanes put 2o value epitixws\n");
      }else{
        printf("SymTable_put den douleuei kanonika\n");
      }

    if(SymTable_put(papadolist,"katerina","67")){
        printf("evales kai 3o value\n");
      }else{
        printf("Provlima sto SymTable_put");
      }

    if(SymTable_remove(papadolist,"eri")){
        printf("afairethike to 2o stoixeio \n");
      }else{
        printf("SymTable_remove apetyxe\n");
      }

    for(i=0;i<1000;i++){
        sprintf(apotelesma, "onoma%d", i);
        SymTable_put(papadolist, apotelesma ,"timi");
    }

    if(SymTable_remove(papadolist,"onoma69")){
          printf("afairethike to onoma69\n");
      }else{
          printf("SymTable_remove apetyxe\n");
      }

    if(!SymTable_get(papadolist,"onoma69")){
        printf("afairethike to onoma69 kanonika\n");
      }else{
        printf("SymTable_remove apetyxe\n");
      }

    if(SymTable_get(papadolist, "onoma47")){
        printf("onoma47: %s stoixeio\n",(char*)SymTable_get(papadolist,
							"onoma47"));
      }else{
        printf("SymTable_get apetyxe\n");
      }

     SymTable_free(papadolist);

     return 0;

}

