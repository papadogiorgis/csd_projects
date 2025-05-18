using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class KOUNA2 : MonoBehaviour
{
   public float speed;
   public void Update()
   {
       transform.position = new Vector3(Mathf.Sin(Time.time + 1.57f) * speed, 1, 10);
   }        
}
